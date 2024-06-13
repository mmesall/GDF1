import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../service-mfpai.test-samples';

import { ServiceMFPAIFormService } from './service-mfpai-form.service';

describe('ServiceMFPAI Form Service', () => {
  let service: ServiceMFPAIFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceMFPAIFormService);
  });

  describe('Service methods', () => {
    describe('createServiceMFPAIFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createServiceMFPAIFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            imageService: expect.any(Object),
            nomService: expect.any(Object),
            chefService: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });

      it('passing IServiceMFPAI should create a new form with FormGroup', () => {
        const formGroup = service.createServiceMFPAIFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            imageService: expect.any(Object),
            nomService: expect.any(Object),
            chefService: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });
    });

    describe('getServiceMFPAI', () => {
      it('should return NewServiceMFPAI for default ServiceMFPAI initial value', () => {
        const formGroup = service.createServiceMFPAIFormGroup(sampleWithNewData);

        const serviceMFPAI = service.getServiceMFPAI(formGroup) as any;

        expect(serviceMFPAI).toMatchObject(sampleWithNewData);
      });

      it('should return NewServiceMFPAI for empty ServiceMFPAI initial value', () => {
        const formGroup = service.createServiceMFPAIFormGroup();

        const serviceMFPAI = service.getServiceMFPAI(formGroup) as any;

        expect(serviceMFPAI).toMatchObject({});
      });

      it('should return IServiceMFPAI', () => {
        const formGroup = service.createServiceMFPAIFormGroup(sampleWithRequiredData);

        const serviceMFPAI = service.getServiceMFPAI(formGroup) as any;

        expect(serviceMFPAI).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IServiceMFPAI should not enable id FormControl', () => {
        const formGroup = service.createServiceMFPAIFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewServiceMFPAI should disable id FormControl', () => {
        const formGroup = service.createServiceMFPAIFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
