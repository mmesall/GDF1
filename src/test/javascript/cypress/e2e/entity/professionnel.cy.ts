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

describe('Professionnel e2e test', () => {
  const professionnelPageUrl = '/professionnel';
  const professionnelPageUrlPattern = new RegExp('/professionnel(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const professionnelSample = { profession: 'occuper vorace vroum', nom: 'spécialiste', prenom: 'de crainte que', cni: 6359 };

  let professionnel;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/professionnels+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/professionnels').as('postEntityRequest');
    cy.intercept('DELETE', '/api/professionnels/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (professionnel) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/professionnels/${professionnel.id}`,
      }).then(() => {
        professionnel = undefined;
      });
    }
  });

  it('Professionnels menu should load Professionnels page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('professionnel');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Professionnel').should('exist');
    cy.url().should('match', professionnelPageUrlPattern);
  });

  describe('Professionnel page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(professionnelPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Professionnel page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/professionnel/new$'));
        cy.getEntityCreateUpdateHeading('Professionnel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', professionnelPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/professionnels',
          body: professionnelSample,
        }).then(({ body }) => {
          professionnel = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/professionnels+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/professionnels?page=0&size=20>; rel="last",<http://localhost/api/professionnels?page=0&size=20>; rel="first"',
              },
              body: [professionnel],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(professionnelPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Professionnel page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('professionnel');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', professionnelPageUrlPattern);
      });

      it('edit button click should load edit Professionnel page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Professionnel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', professionnelPageUrlPattern);
      });

      it('edit button click should load edit Professionnel page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Professionnel');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', professionnelPageUrlPattern);
      });

      it('last delete button click should delete instance of Professionnel', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('professionnel').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', professionnelPageUrlPattern);

        professionnel = undefined;
      });
    });
  });

  describe('new Professionnel page', () => {
    beforeEach(() => {
      cy.visit(`${professionnelPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Professionnel');
    });

    it('should create an instance of Professionnel', () => {
      cy.get(`[data-cy="profession"]`).type('avex porte-parole');
      cy.get(`[data-cy="profession"]`).should('have.value', 'avex porte-parole');

      cy.get(`[data-cy="nom"]`).type('touchant groin groin trop');
      cy.get(`[data-cy="nom"]`).should('have.value', 'touchant groin groin trop');

      cy.get(`[data-cy="prenom"]`).type('communauté étudiante svelte clac');
      cy.get(`[data-cy="prenom"]`).should('have.value', 'communauté étudiante svelte clac');

      cy.get(`[data-cy="dateNaiss"]`).type('2024-06-04');
      cy.get(`[data-cy="dateNaiss"]`).blur();
      cy.get(`[data-cy="dateNaiss"]`).should('have.value', '2024-06-04');

      cy.get(`[data-cy="lieuNaiss"]`).type('mieux chef de cuisine au-dedans de');
      cy.get(`[data-cy="lieuNaiss"]`).should('have.value', 'mieux chef de cuisine au-dedans de');

      cy.get(`[data-cy="sexe"]`).select('FEMME');

      cy.get(`[data-cy="telephone"]`).type('29584');
      cy.get(`[data-cy="telephone"]`).should('have.value', '29584');

      cy.get(`[data-cy="adressePhysique"]`).type('au point que');
      cy.get(`[data-cy="adressePhysique"]`).should('have.value', 'au point que');

      cy.get(`[data-cy="regionResidence"]`).select('KAFFRINE');

      cy.get(`[data-cy="departResidence"]`).select('GOUDIRY');

      cy.get(`[data-cy="email"]`).type('Archange95@gmail.com');
      cy.get(`[data-cy="email"]`).should('have.value', 'Archange95@gmail.com');

      cy.get(`[data-cy="cni"]`).type('10462');
      cy.get(`[data-cy="cni"]`).should('have.value', '10462');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        professionnel = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', professionnelPageUrlPattern);
    });
  });
});
