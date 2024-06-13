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

describe('FormationContinue e2e test', () => {
  const formationContinuePageUrl = '/formation-continue';
  const formationContinuePageUrlPattern = new RegExp('/formation-continue(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const formationContinueSample = {};

  let formationContinue;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/formation-continues+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/formation-continues').as('postEntityRequest');
    cy.intercept('DELETE', '/api/formation-continues/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (formationContinue) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/formation-continues/${formationContinue.id}`,
      }).then(() => {
        formationContinue = undefined;
      });
    }
  });

  it('FormationContinues menu should load FormationContinues page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('formation-continue');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FormationContinue').should('exist');
    cy.url().should('match', formationContinuePageUrlPattern);
  });

  describe('FormationContinue page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(formationContinuePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FormationContinue page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/formation-continue/new$'));
        cy.getEntityCreateUpdateHeading('FormationContinue');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationContinuePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/formation-continues',
          body: formationContinueSample,
        }).then(({ body }) => {
          formationContinue = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/formation-continues+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/formation-continues?page=0&size=20>; rel="last",<http://localhost/api/formation-continues?page=0&size=20>; rel="first"',
              },
              body: [formationContinue],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(formationContinuePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details FormationContinue page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('formationContinue');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationContinuePageUrlPattern);
      });

      it('edit button click should load edit FormationContinue page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FormationContinue');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationContinuePageUrlPattern);
      });

      it('edit button click should load edit FormationContinue page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FormationContinue');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationContinuePageUrlPattern);
      });

      it('last delete button click should delete instance of FormationContinue', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('formationContinue').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationContinuePageUrlPattern);

        formationContinue = undefined;
      });
    });
  });

  describe('new FormationContinue page', () => {
    beforeEach(() => {
      cy.visit(`${formationContinuePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FormationContinue');
    });

    it('should create an instance of FormationContinue', () => {
      cy.get(`[data-cy="nomFormationC"]`).type('novice protester');
      cy.get(`[data-cy="nomFormationC"]`).should('have.value', 'novice protester');

      cy.get(`[data-cy="duree"]`).type('grâce à déborder');
      cy.get(`[data-cy="duree"]`).should('have.value', 'grâce à déborder');

      cy.get(`[data-cy="admission"]`).select('PC');

      cy.get(`[data-cy="diplomeRequis"]`).select('LICENCE');

      cy.get(`[data-cy="niveauEtude"]`).select('TECHNICIENSPECIALISE');

      cy.get(`[data-cy="filiere"]`).select('SANTE_BIOLOGIE_CHIMIE');

      cy.get(`[data-cy="serie"]`).select('S3');

      cy.get(`[data-cy="cfp"]`).select('CFP_FAOYE');

      cy.get(`[data-cy="lycee"]`).select('LTSLL_DAKAR');

      cy.setFieldImageAsBytesOfEntity('ficheFormation', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="libellePC"]`).type('oups régner à partir de');
      cy.get(`[data-cy="libellePC"]`).should('have.value', 'oups régner à partir de');

      cy.get(`[data-cy="montantPriseEnCharge"]`).type('23477.49');
      cy.get(`[data-cy="montantPriseEnCharge"]`).should('have.value', '23477.49');

      cy.get(`[data-cy="coutFormation"]`).type('30380.4');
      cy.get(`[data-cy="coutFormation"]`).should('have.value', '30380.4');

      cy.get(`[data-cy="detailPC"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="detailPC"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="nomDiplome"]`).select('CAP');

      cy.get(`[data-cy="autreDiplome"]`).type('du moment que deviner gestionnaire');
      cy.get(`[data-cy="autreDiplome"]`).should('have.value', 'du moment que deviner gestionnaire');

      cy.get(`[data-cy="nomDebouche"]`).type('comment');
      cy.get(`[data-cy="nomDebouche"]`).should('have.value', 'comment');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        formationContinue = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', formationContinuePageUrlPattern);
    });
  });
});
