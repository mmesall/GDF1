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

describe('Etablissement e2e test', () => {
  const etablissementPageUrl = '/etablissement';
  const etablissementPageUrlPattern = new RegExp('/etablissement(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const etablissementSample = {"region":"DAKAR","departement":"SAINT_LOUIS","statut":"PRIVE"};

  let etablissement;
  // let formation;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/formations',
      body: {"nomFormation":"avant-hier","imageFormation":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=","imageFormationContentType":"unknown","typeFormation":"CONTINUE","duree":"puisque","admission":"PC","diplomeRequis":"ATTESTATION","ficheFormation":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=","ficheFormationContentType":"unknown"},
    }).then(({ body }) => {
      formation = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/etablissements+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/etablissements').as('postEntityRequest');
    cy.intercept('DELETE', '/api/etablissements/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/candidature-es', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/candidature-ps', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/formations', {
      statusCode: 200,
      body: [formation],
    });

  });
   */

  afterEach(() => {
    if (etablissement) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/etablissements/${etablissement.id}`,
      }).then(() => {
        etablissement = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (formation) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/formations/${formation.id}`,
      }).then(() => {
        formation = undefined;
      });
    }
  });
   */

  it('Etablissements menu should load Etablissements page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('etablissement');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Etablissement').should('exist');
    cy.url().should('match', etablissementPageUrlPattern);
  });

  describe('Etablissement page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(etablissementPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Etablissement page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/etablissement/new$'));
        cy.getEntityCreateUpdateHeading('Etablissement');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', etablissementPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/etablissements',
          body: {
            ...etablissementSample,
            formation: formation,
          },
        }).then(({ body }) => {
          etablissement = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/etablissements+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/etablissements?page=0&size=20>; rel="last",<http://localhost/api/etablissements?page=0&size=20>; rel="first"',
              },
              body: [etablissement],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(etablissementPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(etablissementPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response?.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details Etablissement page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('etablissement');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', etablissementPageUrlPattern);
      });

      it('edit button click should load edit Etablissement page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Etablissement');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', etablissementPageUrlPattern);
      });

      it('edit button click should load edit Etablissement page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Etablissement');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', etablissementPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of Etablissement', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('etablissement').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', etablissementPageUrlPattern);

        etablissement = undefined;
      });
    });
  });

  describe('new Etablissement page', () => {
    beforeEach(() => {
      cy.visit(`${etablissementPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Etablissement');
    });

    it.skip('should create an instance of Etablissement', () => {
      cy.get(`[data-cy="nomEtablissement"]`).select('AGNAM_CIVOL');

      cy.setFieldImageAsBytesOfEntity('photo', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="region"]`).select('KOLDA');

      cy.get(`[data-cy="departement"]`).select('MALEM_HODAR');

      cy.get(`[data-cy="email"]`).type('Clementine_Collet78@gmail.com');
      cy.get(`[data-cy="email"]`).should('have.value', 'Clementine_Collet78@gmail.com');

      cy.get(`[data-cy="telephone"]`).type('27350');
      cy.get(`[data-cy="telephone"]`).should('have.value', '27350');

      cy.get(`[data-cy="typeEtablissement"]`).select('LYCEE_TECH');

      cy.get(`[data-cy="statut"]`).select('PRIVE');

      cy.get(`[data-cy="autreRegion"]`).type('lâche spécialiste électorat');
      cy.get(`[data-cy="autreRegion"]`).should('have.value', 'lâche spécialiste électorat');

      cy.get(`[data-cy="autreDepartement"]`).type('responsable');
      cy.get(`[data-cy="autreDepartement"]`).should('have.value', 'responsable');

      cy.get(`[data-cy="cfp"]`).select('CFP_TIVAOUANE');

      cy.get(`[data-cy="lycee"]`).select('LEFP_KOLDA');

      cy.get(`[data-cy="filiere"]`).select('RESTAURATION_ET_HOTELLORIE');

      cy.get(`[data-cy="serie"]`).select('S5');

      cy.get(`[data-cy="autreFiliere"]`).type('dring à travers');
      cy.get(`[data-cy="autreFiliere"]`).should('have.value', 'dring à travers');

      cy.get(`[data-cy="autreSerie"]`).type('toujours tant que tant');
      cy.get(`[data-cy="autreSerie"]`).should('have.value', 'toujours tant que tant');

      cy.get(`[data-cy="autreNomEtablissement"]`).type('même si toc puisque');
      cy.get(`[data-cy="autreNomEtablissement"]`).should('have.value', 'même si toc puisque');

      cy.get(`[data-cy="formation"]`).select([0]);

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        etablissement = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', etablissementPageUrlPattern);
    });
  });
});
