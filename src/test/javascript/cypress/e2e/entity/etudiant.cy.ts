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

describe('Etudiant e2e test', () => {
  const etudiantPageUrl = '/etudiant';
  const etudiantPageUrlPattern = new RegExp('/etudiant(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const etudiantSample = {
    carteEtudiant: 'responsable hors',
    nom: 'au-dehors en guise de dynamique',
    prenom: 'meuh calme entrer',
    cni: 24832,
  };

  let etudiant;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/etudiants+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/etudiants').as('postEntityRequest');
    cy.intercept('DELETE', '/api/etudiants/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (etudiant) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/etudiants/${etudiant.id}`,
      }).then(() => {
        etudiant = undefined;
      });
    }
  });

  it('Etudiants menu should load Etudiants page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('etudiant');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Etudiant').should('exist');
    cy.url().should('match', etudiantPageUrlPattern);
  });

  describe('Etudiant page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(etudiantPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Etudiant page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/etudiant/new$'));
        cy.getEntityCreateUpdateHeading('Etudiant');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', etudiantPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/etudiants',
          body: etudiantSample,
        }).then(({ body }) => {
          etudiant = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/etudiants+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/etudiants?page=0&size=20>; rel="last",<http://localhost/api/etudiants?page=0&size=20>; rel="first"',
              },
              body: [etudiant],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(etudiantPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Etudiant page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('etudiant');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', etudiantPageUrlPattern);
      });

      it('edit button click should load edit Etudiant page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Etudiant');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', etudiantPageUrlPattern);
      });

      it('edit button click should load edit Etudiant page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Etudiant');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', etudiantPageUrlPattern);
      });

      it('last delete button click should delete instance of Etudiant', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('etudiant').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', etudiantPageUrlPattern);

        etudiant = undefined;
      });
    });
  });

  describe('new Etudiant page', () => {
    beforeEach(() => {
      cy.visit(`${etudiantPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Etudiant');
    });

    it('should create an instance of Etudiant', () => {
      cy.get(`[data-cy="carteEtudiant"]`).type('camarade mal');
      cy.get(`[data-cy="carteEtudiant"]`).should('have.value', 'camarade mal');

      cy.get(`[data-cy="nom"]`).type('gens aïe lunatique');
      cy.get(`[data-cy="nom"]`).should('have.value', 'gens aïe lunatique');

      cy.get(`[data-cy="prenom"]`).type('simple');
      cy.get(`[data-cy="prenom"]`).should('have.value', 'simple');

      cy.get(`[data-cy="dateNaiss"]`).type('2024-06-05');
      cy.get(`[data-cy="dateNaiss"]`).blur();
      cy.get(`[data-cy="dateNaiss"]`).should('have.value', '2024-06-05');

      cy.get(`[data-cy="lieuNaiss"]`).type('pour jeune enfant');
      cy.get(`[data-cy="lieuNaiss"]`).should('have.value', 'pour jeune enfant');

      cy.get(`[data-cy="sexe"]`).select('HOMME');

      cy.get(`[data-cy="telephone"]`).type('8587');
      cy.get(`[data-cy="telephone"]`).should('have.value', '8587');

      cy.get(`[data-cy="adressePhysique"]`).type('nonobstant');
      cy.get(`[data-cy="adressePhysique"]`).should('have.value', 'nonobstant');

      cy.get(`[data-cy="regionResidence"]`).select('KAOLACK');

      cy.get(`[data-cy="departResidence"]`).select('NIORO');

      cy.get(`[data-cy="email"]`).type('Florie2@gmail.com');
      cy.get(`[data-cy="email"]`).should('have.value', 'Florie2@gmail.com');

      cy.get(`[data-cy="cni"]`).type('13699');
      cy.get(`[data-cy="cni"]`).should('have.value', '13699');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        etudiant = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', etudiantPageUrlPattern);
    });
  });
});
