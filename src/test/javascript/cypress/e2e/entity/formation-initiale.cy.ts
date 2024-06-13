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

describe('FormationInitiale e2e test', () => {
  const formationInitialePageUrl = '/formation-initiale';
  const formationInitialePageUrlPattern = new RegExp('/formation-initiale(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const formationInitialeSample = {};

  let formationInitiale;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/formation-initiales+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/formation-initiales').as('postEntityRequest');
    cy.intercept('DELETE', '/api/formation-initiales/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (formationInitiale) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/formation-initiales/${formationInitiale.id}`,
      }).then(() => {
        formationInitiale = undefined;
      });
    }
  });

  it('FormationInitiales menu should load FormationInitiales page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('formation-initiale');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FormationInitiale').should('exist');
    cy.url().should('match', formationInitialePageUrlPattern);
  });

  describe('FormationInitiale page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(formationInitialePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FormationInitiale page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/formation-initiale/new$'));
        cy.getEntityCreateUpdateHeading('FormationInitiale');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationInitialePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/formation-initiales',
          body: formationInitialeSample,
        }).then(({ body }) => {
          formationInitiale = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/formation-initiales+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/formation-initiales?page=0&size=20>; rel="last",<http://localhost/api/formation-initiales?page=0&size=20>; rel="first"',
              },
              body: [formationInitiale],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(formationInitialePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details FormationInitiale page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('formationInitiale');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationInitialePageUrlPattern);
      });

      it('edit button click should load edit FormationInitiale page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FormationInitiale');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationInitialePageUrlPattern);
      });

      it('edit button click should load edit FormationInitiale page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FormationInitiale');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationInitialePageUrlPattern);
      });

      it('last delete button click should delete instance of FormationInitiale', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('formationInitiale').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationInitialePageUrlPattern);

        formationInitiale = undefined;
      });
    });
  });

  describe('new FormationInitiale page', () => {
    beforeEach(() => {
      cy.visit(`${formationInitialePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FormationInitiale');
    });

    it('should create an instance of FormationInitiale', () => {
      cy.get(`[data-cy="nomFormationI"]`).type('triathlète oups broum');
      cy.get(`[data-cy="nomFormationI"]`).should('have.value', 'triathlète oups broum');

      cy.get(`[data-cy="duree"]`).type('magenta certainement');
      cy.get(`[data-cy="duree"]`).should('have.value', 'magenta certainement');

      cy.get(`[data-cy="admission"]`).select('CONCOURS');

      cy.get(`[data-cy="diplomeRequis"]`).select('MASTER');

      cy.get(`[data-cy="niveauEtude"]`).select('LICENCE1');

      cy.setFieldImageAsBytesOfEntity('ficheFormation', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="filiere"]`).select('STRUCTURE_PLASTIQUE');

      cy.get(`[data-cy="serie"]`).select('STIDD_E');

      cy.get(`[data-cy="cfp"]`).select('CFP_KEBEMER');

      cy.get(`[data-cy="lycee"]`).select('LTAEB_BIGNONA');

      cy.get(`[data-cy="nomConcours"]`).type('interdire badaboum');
      cy.get(`[data-cy="nomConcours"]`).should('have.value', 'interdire badaboum');

      cy.get(`[data-cy="dateOuverture"]`).type('2024-06-05');
      cy.get(`[data-cy="dateOuverture"]`).blur();
      cy.get(`[data-cy="dateOuverture"]`).should('have.value', '2024-06-05');

      cy.get(`[data-cy="dateCloture"]`).type('2024-06-04');
      cy.get(`[data-cy="dateCloture"]`).blur();
      cy.get(`[data-cy="dateCloture"]`).should('have.value', '2024-06-04');

      cy.get(`[data-cy="dateConcours"]`).type('2024-06-04');
      cy.get(`[data-cy="dateConcours"]`).blur();
      cy.get(`[data-cy="dateConcours"]`).should('have.value', '2024-06-04');

      cy.get(`[data-cy="nomDiplome"]`).select('CAP');

      cy.get(`[data-cy="nomDebouche"]`).type('personnel préserver au-dedans de');
      cy.get(`[data-cy="nomDebouche"]`).should('have.value', 'personnel préserver au-dedans de');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        formationInitiale = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', formationInitialePageUrlPattern);
    });
  });
});
