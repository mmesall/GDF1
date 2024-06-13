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

describe('Dossier e2e test', () => {
  const dossierPageUrl = '/dossier';
  const dossierPageUrlPattern = new RegExp('/dossier(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const dossierSample = {
    prenom: 'du moment que conseil municipal',
    nom: 'oh',
    posteOccupe: 'puis de peur que',
    dateDebut: '2024-06-05',
    dateFin: '2024-06-05',
    nomEntreprise: 'sans paf commis de cuisine',
  };

  let dossier;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/dossiers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/dossiers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/dossiers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (dossier) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/dossiers/${dossier.id}`,
      }).then(() => {
        dossier = undefined;
      });
    }
  });

  it('Dossiers menu should load Dossiers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('dossier');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Dossier').should('exist');
    cy.url().should('match', dossierPageUrlPattern);
  });

  describe('Dossier page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(dossierPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Dossier page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/dossier/new$'));
        cy.getEntityCreateUpdateHeading('Dossier');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dossierPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/dossiers',
          body: dossierSample,
        }).then(({ body }) => {
          dossier = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/dossiers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/dossiers?page=0&size=20>; rel="last",<http://localhost/api/dossiers?page=0&size=20>; rel="first"',
              },
              body: [dossier],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(dossierPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Dossier page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('dossier');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dossierPageUrlPattern);
      });

      it('edit button click should load edit Dossier page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Dossier');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dossierPageUrlPattern);
      });

      it('edit button click should load edit Dossier page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Dossier');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dossierPageUrlPattern);
      });

      it('last delete button click should delete instance of Dossier', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('dossier').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', dossierPageUrlPattern);

        dossier = undefined;
      });
    });
  });

  describe('new Dossier page', () => {
    beforeEach(() => {
      cy.visit(`${dossierPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Dossier');
    });

    it('should create an instance of Dossier', () => {
      cy.get(`[data-cy="numDossier"]`).type('blesser');
      cy.get(`[data-cy="numDossier"]`).should('have.value', 'blesser');

      cy.get(`[data-cy="prenom"]`).type('orange');
      cy.get(`[data-cy="prenom"]`).should('have.value', 'orange');

      cy.get(`[data-cy="nom"]`).type('dans la mesure où égoïste vroum');
      cy.get(`[data-cy="nom"]`).should('have.value', 'dans la mesure où égoïste vroum');

      cy.get(`[data-cy="nomUtilisateur"]`).type('antique');
      cy.get(`[data-cy="nomUtilisateur"]`).should('have.value', 'antique');

      cy.get(`[data-cy="dateNaiss"]`).type('2024-06-04');
      cy.get(`[data-cy="dateNaiss"]`).blur();
      cy.get(`[data-cy="dateNaiss"]`).should('have.value', '2024-06-04');

      cy.get(`[data-cy="lieuNaiss"]`).type('jamais ainsi');
      cy.get(`[data-cy="lieuNaiss"]`).should('have.value', 'jamais ainsi');

      cy.get(`[data-cy="regionNaiss"]`).select('DIOURBEL');

      cy.get(`[data-cy="departementNaiss"]`).select('SALAMATA');

      cy.get(`[data-cy="typePiece"]`).select('CNI');

      cy.get(`[data-cy="numeroPiece"]`).type('104');
      cy.get(`[data-cy="numeroPiece"]`).should('have.value', '104');

      cy.get(`[data-cy="sexe"]`).select('FEMME');

      cy.get(`[data-cy="regionResidence"]`).select('KAFFRINE');

      cy.get(`[data-cy="depResidence"]`).select('DAKAR');

      cy.get(`[data-cy="adresseResidence"]`).type('percer intrépide tolérer');
      cy.get(`[data-cy="adresseResidence"]`).should('have.value', 'percer intrépide tolérer');

      cy.get(`[data-cy="telephone1"]`).type('grrr parlementaire');
      cy.get(`[data-cy="telephone1"]`).should('have.value', 'grrr parlementaire');

      cy.get(`[data-cy="telephone2"]`).type('camarade enrichir alors que');
      cy.get(`[data-cy="telephone2"]`).should('have.value', 'camarade enrichir alors que');

      cy.get(`[data-cy="email"]`).type('Leu.Aubry19@hotmail.fr');
      cy.get(`[data-cy="email"]`).should('have.value', 'Leu.Aubry19@hotmail.fr');

      cy.get(`[data-cy="niveauFormation"]`).select('BT1');

      cy.get(`[data-cy="specialite"]`).select('HABILLEMENT');

      cy.get(`[data-cy="intituleDiplome"]`).type('séculaire');
      cy.get(`[data-cy="intituleDiplome"]`).should('have.value', 'séculaire');

      cy.setFieldImageAsBytesOfEntity('diplome', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="anneeObtention"]`).type('2024-06-05');
      cy.get(`[data-cy="anneeObtention"]`).blur();
      cy.get(`[data-cy="anneeObtention"]`).should('have.value', '2024-06-05');

      cy.get(`[data-cy="lieuObtention"]`).type('euh');
      cy.get(`[data-cy="lieuObtention"]`).should('have.value', 'euh');

      cy.setFieldImageAsBytesOfEntity('cv', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('lettreMotivation', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="profession"]`).type('calme âcre jusque');
      cy.get(`[data-cy="profession"]`).should('have.value', 'calme âcre jusque');

      cy.get(`[data-cy="autreSpecialite"]`).type('caresser manier fournir');
      cy.get(`[data-cy="autreSpecialite"]`).should('have.value', 'caresser manier fournir');

      cy.get(`[data-cy="nomCompetence"]`).type('ouf');
      cy.get(`[data-cy="nomCompetence"]`).should('have.value', 'ouf');

      cy.get(`[data-cy="niveauCompetence"]`).select('DEBUTANT');

      cy.get(`[data-cy="intituleExperience"]`).type('corps enseignant personnel professionnel au moyen de');
      cy.get(`[data-cy="intituleExperience"]`).should('have.value', 'corps enseignant personnel professionnel au moyen de');

      cy.get(`[data-cy="posteOccupe"]`).type('dès que hé');
      cy.get(`[data-cy="posteOccupe"]`).should('have.value', 'dès que hé');

      cy.get(`[data-cy="dateDebut"]`).type('2024-06-05');
      cy.get(`[data-cy="dateDebut"]`).blur();
      cy.get(`[data-cy="dateDebut"]`).should('have.value', '2024-06-05');

      cy.get(`[data-cy="dateFin"]`).type('2024-06-04');
      cy.get(`[data-cy="dateFin"]`).blur();
      cy.get(`[data-cy="dateFin"]`).should('have.value', '2024-06-04');

      cy.get(`[data-cy="nomEntreprise"]`).type('parce que');
      cy.get(`[data-cy="nomEntreprise"]`).should('have.value', 'parce que');

      cy.get(`[data-cy="mission"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="mission"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        dossier = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', dossierPageUrlPattern);
    });
  });
});
