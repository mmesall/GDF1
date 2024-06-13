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

describe('Diplome e2e test', () => {
  const diplomePageUrl = '/diplome';
  const diplomePageUrlPattern = new RegExp('/diplome(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const diplomeSample = { domaine: 'SPORT', document: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=', documentContentType: 'unknown' };

  let diplome;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/diplomes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/diplomes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/diplomes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (diplome) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/diplomes/${diplome.id}`,
      }).then(() => {
        diplome = undefined;
      });
    }
  });

  it('Diplomes menu should load Diplomes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('diplome');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Diplome').should('exist');
    cy.url().should('match', diplomePageUrlPattern);
  });

  describe('Diplome page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(diplomePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Diplome page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/diplome/new$'));
        cy.getEntityCreateUpdateHeading('Diplome');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', diplomePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/diplomes',
          body: diplomeSample,
        }).then(({ body }) => {
          diplome = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/diplomes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/diplomes?page=0&size=20>; rel="last",<http://localhost/api/diplomes?page=0&size=20>; rel="first"',
              },
              body: [diplome],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(diplomePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Diplome page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('diplome');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', diplomePageUrlPattern);
      });

      it('edit button click should load edit Diplome page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Diplome');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', diplomePageUrlPattern);
      });

      it('edit button click should load edit Diplome page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Diplome');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', diplomePageUrlPattern);
      });

      it('last delete button click should delete instance of Diplome', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('diplome').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', diplomePageUrlPattern);

        diplome = undefined;
      });
    });
  });

  describe('new Diplome page', () => {
    beforeEach(() => {
      cy.visit(`${diplomePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Diplome');
    });

    it('should create an instance of Diplome', () => {
      cy.get(`[data-cy="intitule"]`).type('lorsque porte-parole');
      cy.get(`[data-cy="intitule"]`).should('have.value', 'lorsque porte-parole');

      cy.get(`[data-cy="domaine"]`).select('MENUISERIE_BOIS');

      cy.get(`[data-cy="niveau"]`).select('METP');

      cy.get(`[data-cy="mention"]`).select('ASSEZ_BIEN');

      cy.get(`[data-cy="anneeObtention"]`).type('cacher confier de la part de');
      cy.get(`[data-cy="anneeObtention"]`).should('have.value', 'cacher confier de la part de');

      cy.get(`[data-cy="etablissement"]`).type('délégation intrépide');
      cy.get(`[data-cy="etablissement"]`).should('have.value', 'délégation intrépide');

      cy.setFieldImageAsBytesOfEntity('document', 'integration-test.png', 'image/png');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        diplome = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', diplomePageUrlPattern);
    });
  });
});
