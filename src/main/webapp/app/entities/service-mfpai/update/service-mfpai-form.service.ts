import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IServiceMFPAI, NewServiceMFPAI } from '../service-mfpai.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IServiceMFPAI for edit and NewServiceMFPAIFormGroupInput for create.
 */
type ServiceMFPAIFormGroupInput = IServiceMFPAI | PartialWithRequiredKeyOf<NewServiceMFPAI>;

type ServiceMFPAIFormDefaults = Pick<NewServiceMFPAI, 'id'>;

type ServiceMFPAIFormGroupContent = {
  id: FormControl<IServiceMFPAI['id'] | NewServiceMFPAI['id']>;
  imageService: FormControl<IServiceMFPAI['imageService']>;
  imageServiceContentType: FormControl<IServiceMFPAI['imageServiceContentType']>;
  nomService: FormControl<IServiceMFPAI['nomService']>;
  chefService: FormControl<IServiceMFPAI['chefService']>;
  description: FormControl<IServiceMFPAI['description']>;
};

export type ServiceMFPAIFormGroup = FormGroup<ServiceMFPAIFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServiceMFPAIFormService {
  createServiceMFPAIFormGroup(serviceMFPAI: ServiceMFPAIFormGroupInput = { id: null }): ServiceMFPAIFormGroup {
    const serviceMFPAIRawValue = {
      ...this.getFormDefaults(),
      ...serviceMFPAI,
    };
    return new FormGroup<ServiceMFPAIFormGroupContent>({
      id: new FormControl(
        { value: serviceMFPAIRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      imageService: new FormControl(serviceMFPAIRawValue.imageService),
      imageServiceContentType: new FormControl(serviceMFPAIRawValue.imageServiceContentType),
      nomService: new FormControl(serviceMFPAIRawValue.nomService),
      chefService: new FormControl(serviceMFPAIRawValue.chefService, {
        validators: [Validators.required],
      }),
      description: new FormControl(serviceMFPAIRawValue.description),
    });
  }

  getServiceMFPAI(form: ServiceMFPAIFormGroup): IServiceMFPAI | NewServiceMFPAI {
    return form.getRawValue() as IServiceMFPAI | NewServiceMFPAI;
  }

  resetForm(form: ServiceMFPAIFormGroup, serviceMFPAI: ServiceMFPAIFormGroupInput): void {
    const serviceMFPAIRawValue = { ...this.getFormDefaults(), ...serviceMFPAI };
    form.reset(
      {
        ...serviceMFPAIRawValue,
        id: { value: serviceMFPAIRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ServiceMFPAIFormDefaults {
    return {
      id: null,
    };
  }
}
