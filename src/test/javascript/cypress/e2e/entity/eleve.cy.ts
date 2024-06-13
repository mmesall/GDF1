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

describe('Eleve e2e test', () => {
  const elevePageUrl = '/eleve';
  const elevePageUrlPattern = new RegExp('/eleve(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const eleveSample = { nom: 'grandement secours par rapport à', prenom: 'aussitôt que', niveauEtude: 'BT1' };

  let eleve;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/eleves+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/eleves').as('postEntityRequest');
    cy.intercept('DELETE', '/api/eleves/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (eleve) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/eleves/${eleve.id}`,
      }).then(() => {
        eleve = undefined;
      });
    }
  });

  it('Eleves menu should load Eleves page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('eleve');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Eleve').should('exist');
    cy.url().should('match', elevePageUrlPattern);
  });

  describe('Eleve page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(elevePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Eleve page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/eleve/new$'));
        cy.getEntityCreateUpdateHeading('Eleve');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', elevePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/eleves',
          body: eleveSample,
        }).then(({ body }) => {
          eleve = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/eleves+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/eleves?page=0&size=20>; rel="last",<http://localhost/api/eleves?page=0&size=20>; rel="first"',
              },
              body: [eleve],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(elevePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Eleve page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('eleve');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', elevePageUrlPattern);
      });

      it('edit button click should load edit Eleve page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Eleve');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', elevePageUrlPattern);
      });

      it('edit button click should load edit Eleve page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Eleve');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', elevePageUrlPattern);
      });

      it('last delete button click should delete instance of Eleve', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('eleve').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', elevePageUrlPattern);

        eleve = undefined;
      });
    });
  });

  describe('new Eleve page', () => {
    beforeEach(() => {
      cy.visit(`${elevePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Eleve');
    });

    it('should create an instance of Eleve', () => {
      cy.get(`[data-cy="nom"]`).type('vu que ronfler');
      cy.get(`[data-cy="nom"]`).should('have.value', 'vu que ronfler');

      cy.get(`[data-cy="prenom"]`).type('triathlète');
      cy.get(`[data-cy="prenom"]`).should('have.value', 'triathlète');

      cy.get(`[data-cy="dateNaiss"]`).type('2024-06-04');
      cy.get(`[data-cy="dateNaiss"]`).blur();
      cy.get(`[data-cy="dateNaiss"]`).should('have.value', '2024-06-04');

      cy.get(`[data-cy="lieuNaiss"]`).type('dans la mesure où');
      cy.get(`[data-cy="lieuNaiss"]`).should('have.value', 'dans la mesure où');

      cy.get(`[data-cy="sexe"]`).select('FEMME');

      cy.get(`[data-cy="telephone"]`).type('6401');
      cy.get(`[data-cy="telephone"]`).should('have.value', '6401');

      cy.get(`[data-cy="adressePhysique"]`).type('naguère');
      cy.get(`[data-cy="adressePhysique"]`).should('have.value', 'naguère');

      cy.get(`[data-cy="regionResidence"]`).select('ZIGINCHOR');

      cy.get(`[data-cy="departResidence"]`).select('THIES');

      cy.get(`[data-cy="niveauEtude"]`).select('BTI');

      cy.get(`[data-cy="cni"]`).type('8468');
      cy.get(`[data-cy="cni"]`).should('have.value', '8468');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        eleve = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', elevePageUrlPattern);
    });
  });
});
