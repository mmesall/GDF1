import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../etablissement.test-samples';

import { EtablissementFormService } from './etablissement-form.service';

describe('Etablissement Form Service', () => {
  let service: EtablissementFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EtablissementFormService);
  });

  describe('Service methods', () => {
    describe('createEtablissementFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEtablissementFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomEtablissement: expect.any(Object),
            photo: expect.any(Object),
            region: expect.any(Object),
            departement: expect.any(Object),
            email: expect.any(Object),
            telephone: expect.any(Object),
            typeEtablissement: expect.any(Object),
            statut: expect.any(Object),
            autreRegion: expect.any(Object),
            autreDepartement: expect.any(Object),
            cfp: expect.any(Object),
            lycee: expect.any(Object),
            filiere: expect.any(Object),
            serie: expect.any(Object),
            autreFiliere: expect.any(Object),
            autreSerie: expect.any(Object),
            autreNomEtablissement: expect.any(Object),
            formations: expect.any(Object),
          }),
        );
      });

      it('passing IEtablissement should create a new form with FormGroup', () => {
        const formGroup = service.createEtablissementFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomEtablissement: expect.any(Object),
            photo: expect.any(Object),
            region: expect.any(Object),
            departement: expect.any(Object),
            email: expect.any(Object),
            telephone: expect.any(Object),
            typeEtablissement: expect.any(Object),
            statut: expect.any(Object),
            autreRegion: expect.any(Object),
            autreDepartement: expect.any(Object),
            cfp: expect.any(Object),
            lycee: expect.any(Object),
            filiere: expect.any(Object),
            serie: expect.any(Object),
            autreFiliere: expect.any(Object),
            autreSerie: expect.any(Object),
            autreNomEtablissement: expect.any(Object),
            formations: expect.any(Object),
          }),
        );
      });
    });

    describe('getEtablissement', () => {
      it('should return NewEtablissement for default Etablissement initial value', () => {
        const formGroup = service.createEtablissementFormGroup(sampleWithNewData);

        const etablissement = service.getEtablissement(formGroup) as any;

        expect(etablissement).toMatchObject(sampleWithNewData);
      });

      it('should return NewEtablissement for empty Etablissement initial value', () => {
        const formGroup = service.createEtablissementFormGroup();

        const etablissement = service.getEtablissement(formGroup) as any;

        expect(etablissement).toMatchObject({});
      });

      it('should return IEtablissement', () => {
        const formGroup = service.createEtablissementFormGroup(sampleWithRequiredData);

        const etablissement = service.getEtablissement(formGroup) as any;

        expect(etablissement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEtablissement should not enable id FormControl', () => {
        const formGroup = service.createEtablissementFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEtablissement should disable id FormControl', () => {
        const formGroup = service.createEtablissementFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
