import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../dossier.test-samples';

import { DossierFormService } from './dossier-form.service';

describe('Dossier Form Service', () => {
  let service: DossierFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DossierFormService);
  });

  describe('Service methods', () => {
    describe('createDossierFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDossierFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numDossier: expect.any(Object),
            prenom: expect.any(Object),
            nom: expect.any(Object),
            nomUtilisateur: expect.any(Object),
            dateNaiss: expect.any(Object),
            lieuNaiss: expect.any(Object),
            regionNaiss: expect.any(Object),
            departementNaiss: expect.any(Object),
            typePiece: expect.any(Object),
            numeroPiece: expect.any(Object),
            sexe: expect.any(Object),
            regionResidence: expect.any(Object),
            depResidence: expect.any(Object),
            adresseResidence: expect.any(Object),
            telephone1: expect.any(Object),
            telephone2: expect.any(Object),
            email: expect.any(Object),
            niveauFormation: expect.any(Object),
            specialite: expect.any(Object),
            intituleDiplome: expect.any(Object),
            diplome: expect.any(Object),
            anneeObtention: expect.any(Object),
            lieuObtention: expect.any(Object),
            cv: expect.any(Object),
            lettreMotivation: expect.any(Object),
            profession: expect.any(Object),
            autreSpecialite: expect.any(Object),
            nomCompetence: expect.any(Object),
            niveauCompetence: expect.any(Object),
            intituleExperience: expect.any(Object),
            posteOccupe: expect.any(Object),
            dateDebut: expect.any(Object),
            dateFin: expect.any(Object),
            nomEntreprise: expect.any(Object),
            mission: expect.any(Object),
            eleve: expect.any(Object),
            etudiant: expect.any(Object),
            professionnel: expect.any(Object),
          }),
        );
      });

      it('passing IDossier should create a new form with FormGroup', () => {
        const formGroup = service.createDossierFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numDossier: expect.any(Object),
            prenom: expect.any(Object),
            nom: expect.any(Object),
            nomUtilisateur: expect.any(Object),
            dateNaiss: expect.any(Object),
            lieuNaiss: expect.any(Object),
            regionNaiss: expect.any(Object),
            departementNaiss: expect.any(Object),
            typePiece: expect.any(Object),
            numeroPiece: expect.any(Object),
            sexe: expect.any(Object),
            regionResidence: expect.any(Object),
            depResidence: expect.any(Object),
            adresseResidence: expect.any(Object),
            telephone1: expect.any(Object),
            telephone2: expect.any(Object),
            email: expect.any(Object),
            niveauFormation: expect.any(Object),
            specialite: expect.any(Object),
            intituleDiplome: expect.any(Object),
            diplome: expect.any(Object),
            anneeObtention: expect.any(Object),
            lieuObtention: expect.any(Object),
            cv: expect.any(Object),
            lettreMotivation: expect.any(Object),
            profession: expect.any(Object),
            autreSpecialite: expect.any(Object),
            nomCompetence: expect.any(Object),
            niveauCompetence: expect.any(Object),
            intituleExperience: expect.any(Object),
            posteOccupe: expect.any(Object),
            dateDebut: expect.any(Object),
            dateFin: expect.any(Object),
            nomEntreprise: expect.any(Object),
            mission: expect.any(Object),
            eleve: expect.any(Object),
            etudiant: expect.any(Object),
            professionnel: expect.any(Object),
          }),
        );
      });
    });

    describe('getDossier', () => {
      it('should return NewDossier for default Dossier initial value', () => {
        const formGroup = service.createDossierFormGroup(sampleWithNewData);

        const dossier = service.getDossier(formGroup) as any;

        expect(dossier).toMatchObject(sampleWithNewData);
      });

      it('should return NewDossier for empty Dossier initial value', () => {
        const formGroup = service.createDossierFormGroup();

        const dossier = service.getDossier(formGroup) as any;

        expect(dossier).toMatchObject({});
      });

      it('should return IDossier', () => {
        const formGroup = service.createDossierFormGroup(sampleWithRequiredData);

        const dossier = service.getDossier(formGroup) as any;

        expect(dossier).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDossier should not enable id FormControl', () => {
        const formGroup = service.createDossierFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDossier should disable id FormControl', () => {
        const formGroup = service.createDossierFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
