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

describe('ServiceMFPAI e2e test', () => {
  const serviceMFPAIPageUrl = '/service-mfpai';
  const serviceMFPAIPageUrlPattern = new RegExp('/service-mfpai(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const serviceMFPAISample = { chefService: 'ramper au-devant' };

  let serviceMFPAI;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/service-mfpais+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/service-mfpais').as('postEntityRequest');
    cy.intercept('DELETE', '/api/service-mfpais/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (serviceMFPAI) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/service-mfpais/${serviceMFPAI.id}`,
      }).then(() => {
        serviceMFPAI = undefined;
      });
    }
  });

  it('ServiceMFPAIS menu should load ServiceMFPAIS page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('service-mfpai');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ServiceMFPAI').should('exist');
    cy.url().should('match', serviceMFPAIPageUrlPattern);
  });

  describe('ServiceMFPAI page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(serviceMFPAIPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ServiceMFPAI page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/service-mfpai/new$'));
        cy.getEntityCreateUpdateHeading('ServiceMFPAI');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', serviceMFPAIPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/service-mfpais',
          body: serviceMFPAISample,
        }).then(({ body }) => {
          serviceMFPAI = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/service-mfpais+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/service-mfpais?page=0&size=20>; rel="last",<http://localhost/api/service-mfpais?page=0&size=20>; rel="first"',
              },
              body: [serviceMFPAI],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(serviceMFPAIPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ServiceMFPAI page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('serviceMFPAI');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', serviceMFPAIPageUrlPattern);
      });

      it('edit button click should load edit ServiceMFPAI page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ServiceMFPAI');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', serviceMFPAIPageUrlPattern);
      });

      it('edit button click should load edit ServiceMFPAI page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ServiceMFPAI');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', serviceMFPAIPageUrlPattern);
      });

      it('last delete button click should delete instance of ServiceMFPAI', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('serviceMFPAI').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', serviceMFPAIPageUrlPattern);

        serviceMFPAI = undefined;
      });
    });
  });

  describe('new ServiceMFPAI page', () => {
    beforeEach(() => {
      cy.visit(`${serviceMFPAIPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ServiceMFPAI');
    });

    it('should create an instance of ServiceMFPAI', () => {
      cy.setFieldImageAsBytesOfEntity('imageService', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="nomService"]`).type('tellement au point que');
      cy.get(`[data-cy="nomService"]`).should('have.value', 'tellement au point que');

      cy.get(`[data-cy="chefService"]`).type('diététiste');
      cy.get(`[data-cy="chefService"]`).should('have.value', 'diététiste');

      cy.get(`[data-cy="description"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="description"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        serviceMFPAI = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', serviceMFPAIPageUrlPattern);
    });
  });
});
