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

describe('Demandeur e2e test', () => {
  const demandeurPageUrl = '/demandeur';
  const demandeurPageUrlPattern = new RegExp('/demandeur(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const demandeurSample = {};

  let demandeur;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/demandeurs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/demandeurs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/demandeurs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (demandeur) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/demandeurs/${demandeur.id}`,
      }).then(() => {
        demandeur = undefined;
      });
    }
  });

  it('Demandeurs menu should load Demandeurs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('demandeur');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Demandeur').should('exist');
    cy.url().should('match', demandeurPageUrlPattern);
  });

  describe('Demandeur page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(demandeurPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Demandeur page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/demandeur/new$'));
        cy.getEntityCreateUpdateHeading('Demandeur');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', demandeurPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/demandeurs',
          body: demandeurSample,
        }).then(({ body }) => {
          demandeur = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/demandeurs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/demandeurs?page=0&size=20>; rel="last",<http://localhost/api/demandeurs?page=0&size=20>; rel="first"',
              },
              body: [demandeur],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(demandeurPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Demandeur page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('demandeur');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', demandeurPageUrlPattern);
      });

      it('edit button click should load edit Demandeur page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Demandeur');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', demandeurPageUrlPattern);
      });

      it('edit button click should load edit Demandeur page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Demandeur');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', demandeurPageUrlPattern);
      });

      it('last delete button click should delete instance of Demandeur', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('demandeur').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', demandeurPageUrlPattern);

        demandeur = undefined;
      });
    });
  });

  describe('new Demandeur page', () => {
    beforeEach(() => {
      cy.visit(`${demandeurPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Demandeur');
    });

    it('should create an instance of Demandeur', () => {
      cy.get(`[data-cy="nom"]`).type('alentour');
      cy.get(`[data-cy="nom"]`).should('have.value', 'alentour');

      cy.get(`[data-cy="prenom"]`).type('pff quasi plaire');
      cy.get(`[data-cy="prenom"]`).should('have.value', 'pff quasi plaire');

      cy.get(`[data-cy="dateNaiss"]`).type('2024-06-04');
      cy.get(`[data-cy="dateNaiss"]`).blur();
      cy.get(`[data-cy="dateNaiss"]`).should('have.value', '2024-06-04');

      cy.get(`[data-cy="lieuNaiss"]`).type('diplomate');
      cy.get(`[data-cy="lieuNaiss"]`).should('have.value', 'diplomate');

      cy.get(`[data-cy="sexe"]`).select('FEMME');

      cy.get(`[data-cy="telephone"]`).type('28718');
      cy.get(`[data-cy="telephone"]`).should('have.value', '28718');

      cy.get(`[data-cy="email"]`).type('Xavier_Leclercq16@gmail.com');
      cy.get(`[data-cy="email"]`).should('have.value', 'Xavier_Leclercq16@gmail.com');

      cy.get(`[data-cy="profil"]`).select('ETUDIANT');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        demandeur = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', demandeurPageUrlPattern);
    });
  });
});
