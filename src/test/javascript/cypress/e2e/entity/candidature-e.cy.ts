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

describe('CandidatureE e2e test', () => {
  const candidatureEPageUrl = '/candidature-e';
  const candidatureEPageUrlPattern = new RegExp('/candidature-e(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const candidatureESample = {};

  let candidatureE;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/candidature-es+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/candidature-es').as('postEntityRequest');
    cy.intercept('DELETE', '/api/candidature-es/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (candidatureE) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/candidature-es/${candidatureE.id}`,
      }).then(() => {
        candidatureE = undefined;
      });
    }
  });

  it('CandidatureES menu should load CandidatureES page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('candidature-e');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CandidatureE').should('exist');
    cy.url().should('match', candidatureEPageUrlPattern);
  });

  describe('CandidatureE page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(candidatureEPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CandidatureE page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/candidature-e/new$'));
        cy.getEntityCreateUpdateHeading('CandidatureE');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', candidatureEPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/candidature-es',
          body: candidatureESample,
        }).then(({ body }) => {
          candidatureE = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/candidature-es+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/candidature-es?page=0&size=20>; rel="last",<http://localhost/api/candidature-es?page=0&size=20>; rel="first"',
              },
              body: [candidatureE],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(candidatureEPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CandidatureE page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('candidatureE');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', candidatureEPageUrlPattern);
      });

      it('edit button click should load edit CandidatureE page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CandidatureE');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', candidatureEPageUrlPattern);
      });

      it('edit button click should load edit CandidatureE page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CandidatureE');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', candidatureEPageUrlPattern);
      });

      it('last delete button click should delete instance of CandidatureE', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('candidatureE').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', candidatureEPageUrlPattern);

        candidatureE = undefined;
      });
    });
  });

  describe('new CandidatureE page', () => {
    beforeEach(() => {
      cy.visit(`${candidatureEPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CandidatureE');
    });

    it('should create an instance of CandidatureE', () => {
      cy.get(`[data-cy="offreFormation"]`).select('AGROALIMENTAIRE');

      cy.get(`[data-cy="dateDebutOffre"]`).type('2024-06-05');
      cy.get(`[data-cy="dateDebutOffre"]`).blur();
      cy.get(`[data-cy="dateDebutOffre"]`).should('have.value', '2024-06-05');

      cy.get(`[data-cy="dateFinOffre"]`).type('2024-06-05');
      cy.get(`[data-cy="dateFinOffre"]`).blur();
      cy.get(`[data-cy="dateFinOffre"]`).should('have.value', '2024-06-05');

      cy.get(`[data-cy="dateDepot"]`).type('2024-06-04');
      cy.get(`[data-cy="dateDepot"]`).blur();
      cy.get(`[data-cy="dateDepot"]`).should('have.value', '2024-06-04');

      cy.get(`[data-cy="resultat"]`).select('APPROUVE');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        candidatureE = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', candidatureEPageUrlPattern);
    });
  });
});
