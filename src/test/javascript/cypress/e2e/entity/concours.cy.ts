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

describe('Concours e2e test', () => {
  const concoursPageUrl = '/concours';
  const concoursPageUrlPattern = new RegExp('/concours(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const concoursSample = {};

  let concours;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/concours+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/concours').as('postEntityRequest');
    cy.intercept('DELETE', '/api/concours/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (concours) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/concours/${concours.id}`,
      }).then(() => {
        concours = undefined;
      });
    }
  });

  it('Concours menu should load Concours page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('concours');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Concours').should('exist');
    cy.url().should('match', concoursPageUrlPattern);
  });

  describe('Concours page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(concoursPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Concours page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/concours/new$'));
        cy.getEntityCreateUpdateHeading('Concours');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', concoursPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/concours',
          body: concoursSample,
        }).then(({ body }) => {
          concours = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/concours+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/concours?page=0&size=20>; rel="last",<http://localhost/api/concours?page=0&size=20>; rel="first"',
              },
              body: [concours],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(concoursPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Concours page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('concours');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', concoursPageUrlPattern);
      });

      it('edit button click should load edit Concours page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Concours');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', concoursPageUrlPattern);
      });

      it('edit button click should load edit Concours page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Concours');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', concoursPageUrlPattern);
      });

      it('last delete button click should delete instance of Concours', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('concours').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', concoursPageUrlPattern);

        concours = undefined;
      });
    });
  });

  describe('new Concours page', () => {
    beforeEach(() => {
      cy.visit(`${concoursPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Concours');
    });

    it('should create an instance of Concours', () => {
      cy.get(`[data-cy="nomConcours"]`).type('entre-temps opérer sauvage');
      cy.get(`[data-cy="nomConcours"]`).should('have.value', 'entre-temps opérer sauvage');

      cy.get(`[data-cy="nomEtablissement"]`).select('CNQP');

      cy.get(`[data-cy="niveauEtude"]`).select('EQUIVALENT_BFEM');

      cy.get(`[data-cy="dateOuverture"]`).type('2024-06-05');
      cy.get(`[data-cy="dateOuverture"]`).blur();
      cy.get(`[data-cy="dateOuverture"]`).should('have.value', '2024-06-05');

      cy.get(`[data-cy="dateCloture"]`).type('2024-06-05');
      cy.get(`[data-cy="dateCloture"]`).blur();
      cy.get(`[data-cy="dateCloture"]`).should('have.value', '2024-06-05');

      cy.get(`[data-cy="dateConcours"]`).type('2024-06-04');
      cy.get(`[data-cy="dateConcours"]`).blur();
      cy.get(`[data-cy="dateConcours"]`).should('have.value', '2024-06-04');

      cy.setFieldImageAsBytesOfEntity('affiche', 'integration-test.png', 'image/png');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        concours = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', concoursPageUrlPattern);
    });
  });
});
