import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../diplome.test-samples';

import { DiplomeFormService } from './diplome-form.service';

describe('Diplome Form Service', () => {
  let service: DiplomeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DiplomeFormService);
  });

  describe('Service methods', () => {
    describe('createDiplomeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDiplomeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            intitule: expect.any(Object),
            domaine: expect.any(Object),
            niveau: expect.any(Object),
            mention: expect.any(Object),
            anneeObtention: expect.any(Object),
            etablissement: expect.any(Object),
            document: expect.any(Object),
            eleve: expect.any(Object),
            etudiant: expect.any(Object),
            professionnel: expect.any(Object),
            demandeur: expect.any(Object),
          }),
        );
      });

      it('passing IDiplome should create a new form with FormGroup', () => {
        const formGroup = service.createDiplomeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            intitule: expect.any(Object),
            domaine: expect.any(Object),
            niveau: expect.any(Object),
            mention: expect.any(Object),
            anneeObtention: expect.any(Object),
            etablissement: expect.any(Object),
            document: expect.any(Object),
            eleve: expect.any(Object),
            etudiant: expect.any(Object),
            professionnel: expect.any(Object),
            demandeur: expect.any(Object),
          }),
        );
      });
    });

    describe('getDiplome', () => {
      it('should return NewDiplome for default Diplome initial value', () => {
        const formGroup = service.createDiplomeFormGroup(sampleWithNewData);

        const diplome = service.getDiplome(formGroup) as any;

        expect(diplome).toMatchObject(sampleWithNewData);
      });

      it('should return NewDiplome for empty Diplome initial value', () => {
        const formGroup = service.createDiplomeFormGroup();

        const diplome = service.getDiplome(formGroup) as any;

        expect(diplome).toMatchObject({});
      });

      it('should return IDiplome', () => {
        const formGroup = service.createDiplomeFormGroup(sampleWithRequiredData);

        const diplome = service.getDiplome(formGroup) as any;

        expect(diplome).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDiplome should not enable id FormControl', () => {
        const formGroup = service.createDiplomeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDiplome should disable id FormControl', () => {
        const formGroup = service.createDiplomeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
