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

describe('Formation e2e test', () => {
  const formationPageUrl = '/formation';
  const formationPageUrlPattern = new RegExp('/formation(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const formationSample = {};

  let formation;
  // let etablissement;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/etablissements',
      body: {"nomEtablissement":"CENTRE_REFERENCE_SEDHIOU","photo":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=","photoContentType":"unknown","region":"DIOURBEL","departement":"LOUGA","email":"Pauline_Mathieu@hotmail.fr","telephone":30154,"typeEtablissement":"CFP","statut":"PUBLIC","autreRegion":"habile","autreDepartement":"même si séculaire","cfp":"CFP_DAKAR","lycee":"LTAB_DIOURBEL","filiere":"MENUISERIE_BOIS","serie":"F6","autreFiliere":"nager oups","autreSerie":"approximativement délégation","autreNomEtablissement":"du fait que oh touchant"},
    }).then(({ body }) => {
      etablissement = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/formations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/formations').as('postEntityRequest');
    cy.intercept('DELETE', '/api/formations/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/etablissements', {
      statusCode: 200,
      body: [etablissement],
    });

    cy.intercept('GET', '/api/prise-en-charges', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/formation-initiales', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/formation-continues', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/concours', {
      statusCode: 200,
      body: [],
    });

  });
   */

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

  /* Disabled due to incompatibility
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
   */

  it('Formations menu should load Formations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('formation');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Formation').should('exist');
    cy.url().should('match', formationPageUrlPattern);
  });

  describe('Formation page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(formationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Formation page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/formation/new$'));
        cy.getEntityCreateUpdateHeading('Formation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/formations',
          body: {
            ...formationSample,
            etablissement: etablissement,
          },
        }).then(({ body }) => {
          formation = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/formations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/formations?page=0&size=20>; rel="last",<http://localhost/api/formations?page=0&size=20>; rel="first"',
              },
              body: [formation],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(formationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(formationPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response?.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details Formation page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('formation');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationPageUrlPattern);
      });

      it('edit button click should load edit Formation page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Formation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationPageUrlPattern);
      });

      it('edit button click should load edit Formation page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Formation');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationPageUrlPattern);
      });

      it.skip('last delete button click should delete instance of Formation', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('formation').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', formationPageUrlPattern);

        formation = undefined;
      });
    });
  });

  describe('new Formation page', () => {
    beforeEach(() => {
      cy.visit(`${formationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Formation');
    });

    it.skip('should create an instance of Formation', () => {
      cy.get(`[data-cy="nomFormation"]`).type('jusqu’à ce que rose');
      cy.get(`[data-cy="nomFormation"]`).should('have.value', 'jusqu’à ce que rose');

      cy.setFieldImageAsBytesOfEntity('imageFormation', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="typeFormation"]`).select('INITIALE');

      cy.get(`[data-cy="duree"]`).type('hirsute essuyer travailler');
      cy.get(`[data-cy="duree"]`).should('have.value', 'hirsute essuyer travailler');

      cy.get(`[data-cy="admission"]`).select('PC');

      cy.get(`[data-cy="diplomeRequis"]`).select('ATTESTATION');

      cy.setFieldImageAsBytesOfEntity('ficheFormation', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="etablissement"]`).select([0]);

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        formation = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', formationPageUrlPattern);
    });
  });
});
