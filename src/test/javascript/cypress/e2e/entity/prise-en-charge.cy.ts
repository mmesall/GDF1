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

describe('PriseEnCharge e2e test', () => {
  const priseEnChargePageUrl = '/prise-en-charge';
  const priseEnChargePageUrlPattern = new RegExp('/prise-en-charge(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const priseEnChargeSample = {};

  let priseEnCharge;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/prise-en-charges+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/prise-en-charges').as('postEntityRequest');
    cy.intercept('DELETE', '/api/prise-en-charges/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (priseEnCharge) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/prise-en-charges/${priseEnCharge.id}`,
      }).then(() => {
        priseEnCharge = undefined;
      });
    }
  });

  it('PriseEnCharges menu should load PriseEnCharges page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('prise-en-charge');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PriseEnCharge').should('exist');
    cy.url().should('match', priseEnChargePageUrlPattern);
  });

  describe('PriseEnCharge page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(priseEnChargePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PriseEnCharge page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/prise-en-charge/new$'));
        cy.getEntityCreateUpdateHeading('PriseEnCharge');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', priseEnChargePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/prise-en-charges',
          body: priseEnChargeSample,
        }).then(({ body }) => {
          priseEnCharge = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/prise-en-charges+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/prise-en-charges?page=0&size=20>; rel="last",<http://localhost/api/prise-en-charges?page=0&size=20>; rel="first"',
              },
              body: [priseEnCharge],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(priseEnChargePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PriseEnCharge page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('priseEnCharge');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', priseEnChargePageUrlPattern);
      });

      it('edit button click should load edit PriseEnCharge page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PriseEnCharge');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', priseEnChargePageUrlPattern);
      });

      it('edit button click should load edit PriseEnCharge page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PriseEnCharge');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', priseEnChargePageUrlPattern);
      });

      it('last delete button click should delete instance of PriseEnCharge', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('priseEnCharge').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', priseEnChargePageUrlPattern);

        priseEnCharge = undefined;
      });
    });
  });

  describe('new PriseEnCharge page', () => {
    beforeEach(() => {
      cy.visit(`${priseEnChargePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PriseEnCharge');
    });

    it('should create an instance of PriseEnCharge', () => {
      cy.get(`[data-cy="libelle"]`).type('croire commis nier');
      cy.get(`[data-cy="libelle"]`).should('have.value', 'croire commis nier');

      cy.get(`[data-cy="montantPC"]`).type('24530.29');
      cy.get(`[data-cy="montantPC"]`).should('have.value', '24530.29');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        priseEnCharge = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', priseEnChargePageUrlPattern);
    });
  });
});
