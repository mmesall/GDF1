import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Experience e2e test', () => {
  const experiencePageUrl = '/experience';
  const experiencePageUrlPattern = new RegExp('/experience(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const experienceSample = {
    dateDebut: '2024-06-05',
    dateFin: '2024-06-05',
    nomEntreprise: 'dans la mesure où drelin',
    posteOccupe: 'en faveur de',
  };

  let experience;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/experiences+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/experiences').as('postEntityRequest');
    cy.intercept('DELETE', '/api/experiences/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (experience) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/experiences/${experience.id}`,
      }).then(() => {
        experience = undefined;
      });
    }
  });

  it('Experiences menu should load Experiences page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('experience');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Experience').should('exist');
    cy.url().should('match', experiencePageUrlPattern);
  });

  describe('Experience page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(experiencePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Experience page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/experience/new$'));
        cy.getEntityCreateUpdateHeading('Experience');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', experiencePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/experiences',
          body: experienceSample,
        }).then(({ body }) => {
          experience = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/experiences+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/experiences?page=0&size=20>; rel="last",<http://localhost/api/experiences?page=0&size=20>; rel="first"',
              },
              body: [experience],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(experiencePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Experience page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('experience');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', experiencePageUrlPattern);
      });

      it('edit button click should load edit Experience page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Experience');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', experiencePageUrlPattern);
      });

      it('edit button click should load edit Experience page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Experience');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', experiencePageUrlPattern);
      });

      it('last delete button click should delete instance of Experience', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('experience').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', experiencePageUrlPattern);

        experience = undefined;
      });
    });
  });

  describe('new Experience page', () => {
    beforeEach(() => {
      cy.visit(`${experiencePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Experience');
    });

    it('should create an instance of Experience', () => {
      cy.get(`[data-cy="dateDebut"]`).type('2024-06-04');
      cy.get(`[data-cy="dateDebut"]`).blur();
      cy.get(`[data-cy="dateDebut"]`).should('have.value', '2024-06-04');

      cy.get(`[data-cy="dateFin"]`).type('2024-06-05');
      cy.get(`[data-cy="dateFin"]`).blur();
      cy.get(`[data-cy="dateFin"]`).should('have.value', '2024-06-05');

      cy.get(`[data-cy="nomEntreprise"]`).type('grandement turquoise');
      cy.get(`[data-cy="nomEntreprise"]`).should('have.value', 'grandement turquoise');

      cy.get(`[data-cy="posteOccupe"]`).type('pendant que équipe de recherche');
      cy.get(`[data-cy="posteOccupe"]`).should('have.value', 'pendant que équipe de recherche');

      cy.get(`[data-cy="mission"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="mission"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        experience = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', experiencePageUrlPattern);
    });
  });
});
