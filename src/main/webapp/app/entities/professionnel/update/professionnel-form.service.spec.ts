import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../professionnel.test-samples';

import { ProfessionnelFormService } from './professionnel-form.service';

describe('Professionnel Form Service', () => {
  let service: ProfessionnelFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfessionnelFormService);
  });

  describe('Service methods', () => {
    describe('createProfessionnelFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProfessionnelFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            profession: expect.any(Object),
            nom: expect.any(Object),
            prenom: expect.any(Object),
            dateNaiss: expect.any(Object),
            lieuNaiss: expect.any(Object),
            sexe: expect.any(Object),
            telephone: expect.any(Object),
            adressePhysique: expect.any(Object),
            regionResidence: expect.any(Object),
            departResidence: expect.any(Object),
            email: expect.any(Object),
            cni: expect.any(Object),
            user: expect.any(Object),
          }),
        );
      });

      it('passing IProfessionnel should create a new form with FormGroup', () => {
        const formGroup = service.createProfessionnelFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            profession: expect.any(Object),
            nom: expect.any(Object),
            prenom: expect.any(Object),
            dateNaiss: expect.any(Object),
            lieuNaiss: expect.any(Object),
            sexe: expect.any(Object),
            telephone: expect.any(Object),
            adressePhysique: expect.any(Object),
            regionResidence: expect.any(Object),
            departResidence: expect.any(Object),
            email: expect.any(Object),
            cni: expect.any(Object),
            user: expect.any(Object),
          }),
        );
      });
    });

    describe('getProfessionnel', () => {
      it('should return NewProfessionnel for default Professionnel initial value', () => {
        const formGroup = service.createProfessionnelFormGroup(sampleWithNewData);

        const professionnel = service.getProfessionnel(formGroup) as any;

        expect(professionnel).toMatchObject(sampleWithNewData);
      });

      it('should return NewProfessionnel for empty Professionnel initial value', () => {
        const formGroup = service.createProfessionnelFormGroup();

        const professionnel = service.getProfessionnel(formGroup) as any;

        expect(professionnel).toMatchObject({});
      });

      it('should return IProfessionnel', () => {
        const formGroup = service.createProfessionnelFormGroup(sampleWithRequiredData);

        const professionnel = service.getProfessionnel(formGroup) as any;

        expect(professionnel).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProfessionnel should not enable id FormControl', () => {
        const formGroup = service.createProfessionnelFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProfessionnel should disable id FormControl', () => {
        const formGroup = service.createProfessionnelFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
