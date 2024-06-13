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

describe('CandidatureP e2e test', () => {
  const candidaturePPageUrl = '/candidature-p';
  const candidaturePPageUrlPattern = new RegExp('/candidature-p(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const candidaturePSample = {};

  let candidatureP;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/candidature-ps+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/candidature-ps').as('postEntityRequest');
    cy.intercept('DELETE', '/api/candidature-ps/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (candidatureP) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/candidature-ps/${candidatureP.id}`,
      }).then(() => {
        candidatureP = undefined;
      });
    }
  });

  it('CandidaturePS menu should load CandidaturePS page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('candidature-p');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CandidatureP').should('exist');
    cy.url().should('match', candidaturePPageUrlPattern);
  });

  describe('CandidatureP page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(candidaturePPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CandidatureP page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/candidature-p/new$'));
        cy.getEntityCreateUpdateHeading('CandidatureP');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', candidaturePPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/candidature-ps',
          body: candidaturePSample,
        }).then(({ body }) => {
          candidatureP = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/candidature-ps+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/candidature-ps?page=0&size=20>; rel="last",<http://localhost/api/candidature-ps?page=0&size=20>; rel="first"',
              },
              body: [candidatureP],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(candidaturePPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CandidatureP page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('candidatureP');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', candidaturePPageUrlPattern);
      });

      it('edit button click should load edit CandidatureP page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CandidatureP');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', candidaturePPageUrlPattern);
      });

      it('edit button click should load edit CandidatureP page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CandidatureP');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', candidaturePPageUrlPattern);
      });

      it('last delete button click should delete instance of CandidatureP', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('candidatureP').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', candidaturePPageUrlPattern);

        candidatureP = undefined;
      });
    });
  });

  describe('new CandidatureP page', () => {
    beforeEach(() => {
      cy.visit(`${candidaturePPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CandidatureP');
    });

    it('should create an instance of CandidatureP', () => {
      cy.get(`[data-cy="offreFormation"]`).select('PECHE');

      cy.get(`[data-cy="dateDebutOffre"]`).type('2024-06-05');
      cy.get(`[data-cy="dateDebutOffre"]`).blur();
      cy.get(`[data-cy="dateDebutOffre"]`).should('have.value', '2024-06-05');

      cy.get(`[data-cy="dateFinOffre"]`).type('2024-06-05');
      cy.get(`[data-cy="dateFinOffre"]`).blur();
      cy.get(`[data-cy="dateFinOffre"]`).should('have.value', '2024-06-05');

      cy.get(`[data-cy="dateDepot"]`).type('2024-06-05');
      cy.get(`[data-cy="dateDepot"]`).blur();
      cy.get(`[data-cy="dateDepot"]`).should('have.value', '2024-06-05');

      cy.get(`[data-cy="resultat"]`).select('VALIDE');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        candidatureP = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', candidaturePPageUrlPattern);
    });
  });
});
