import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IExperience, NewExperience } from '../experience.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IExperience for edit and NewExperienceFormGroupInput for create.
 */
type ExperienceFormGroupInput = IExperience | PartialWithRequiredKeyOf<NewExperience>;

type ExperienceFormDefaults = Pick<NewExperience, 'id'>;

type ExperienceFormGroupContent = {
  id: FormControl<IExperience['id'] | NewExperience['id']>;
  dateDebut: FormControl<IExperience['dateDebut']>;
  dateFin: FormControl<IExperience['dateFin']>;
  nomEntreprise: FormControl<IExperience['nomEntreprise']>;
  posteOccupe: FormControl<IExperience['posteOccupe']>;
  mission: FormControl<IExperience['mission']>;
  eleve: FormControl<IExperience['eleve']>;
  etudiant: FormControl<IExperience['etudiant']>;
  professionnel: FormControl<IExperience['professionnel']>;
  demandeur: FormControl<IExperience['demandeur']>;
};

export type ExperienceFormGroup = FormGroup<ExperienceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ExperienceFormService {
  createExperienceFormGroup(experience: ExperienceFormGroupInput = { id: null }): ExperienceFormGroup {
    const experienceRawValue = {
      ...this.getFormDefaults(),
      ...experience,
    };
    return new FormGroup<ExperienceFormGroupContent>({
      id: new FormControl(
        { value: experienceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dateDebut: new FormControl(experienceRawValue.dateDebut, {
        validators: [Validators.required],
      }),
      dateFin: new FormControl(experienceRawValue.dateFin, {
        validators: [Validators.required],
      }),
      nomEntreprise: new FormControl(experienceRawValue.nomEntreprise, {
        validators: [Validators.required],
      }),
      posteOccupe: new FormControl(experienceRawValue.posteOccupe, {
        validators: [Validators.required],
      }),
      mission: new FormControl(experienceRawValue.mission),
      eleve: new FormControl(experienceRawValue.eleve),
      etudiant: new FormControl(experienceRawValue.etudiant),
      professionnel: new FormControl(experienceRawValue.professionnel),
      demandeur: new FormControl(experienceRawValue.demandeur),
    });
  }

  getExperience(form: ExperienceFormGroup): IExperience | NewExperience {
    return form.getRawValue() as IExperience | NewExperience;
  }

  resetForm(form: ExperienceFormGroup, experience: ExperienceFormGroupInput): void {
    const experienceRawValue = { ...this.getFormDefaults(), ...experience };
    form.reset(
      {
        ...experienceRawValue,
        id: { value: experienceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ExperienceFormDefaults {
    return {
      id: null,
    };
  }
}
