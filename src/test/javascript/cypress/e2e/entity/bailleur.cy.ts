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

describe('Bailleur e2e test', () => {
  const bailleurPageUrl = '/bailleur';
  const bailleurPageUrlPattern = new RegExp('/bailleur(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bailleurSample = { nomBailleur: 'aux alentours de ouah' };

  let bailleur;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/bailleurs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/bailleurs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/bailleurs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (bailleur) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/bailleurs/${bailleur.id}`,
      }).then(() => {
        bailleur = undefined;
      });
    }
  });

  it('Bailleurs menu should load Bailleurs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('bailleur');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Bailleur').should('exist');
    cy.url().should('match', bailleurPageUrlPattern);
  });

  describe('Bailleur page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bailleurPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Bailleur page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/bailleur/new$'));
        cy.getEntityCreateUpdateHeading('Bailleur');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bailleurPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/bailleurs',
          body: bailleurSample,
        }).then(({ body }) => {
          bailleur = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/bailleurs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/bailleurs?page=0&size=20>; rel="last",<http://localhost/api/bailleurs?page=0&size=20>; rel="first"',
              },
              body: [bailleur],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(bailleurPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Bailleur page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('bailleur');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bailleurPageUrlPattern);
      });

      it('edit button click should load edit Bailleur page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Bailleur');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bailleurPageUrlPattern);
      });

      it('edit button click should load edit Bailleur page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Bailleur');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bailleurPageUrlPattern);
      });

      it('last delete button click should delete instance of Bailleur', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('bailleur').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bailleurPageUrlPattern);

        bailleur = undefined;
      });
    });
  });

  describe('new Bailleur page', () => {
    beforeEach(() => {
      cy.visit(`${bailleurPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Bailleur');
    });

    it('should create an instance of Bailleur', () => {
      cy.get(`[data-cy="nomBailleur"]`).type('ouah encore hâter');
      cy.get(`[data-cy="nomBailleur"]`).should('have.value', 'ouah encore hâter');

      cy.get(`[data-cy="budgetPrevu"]`).type('12561.68');
      cy.get(`[data-cy="budgetPrevu"]`).should('have.value', '12561.68');

      cy.get(`[data-cy="budgetDepense"]`).type('27037.88');
      cy.get(`[data-cy="budgetDepense"]`).should('have.value', '27037.88');

      cy.get(`[data-cy="budgetRestant"]`).type('4813.14');
      cy.get(`[data-cy="budgetRestant"]`).should('have.value', '4813.14');

      cy.get(`[data-cy="nbrePC"]`).type('3076');
      cy.get(`[data-cy="nbrePC"]`).should('have.value', '3076');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        bailleur = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', bailleurPageUrlPattern);
    });
  });
});
