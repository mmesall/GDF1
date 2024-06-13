import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProfessionnel, NewProfessionnel } from '../professionnel.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProfessionnel for edit and NewProfessionnelFormGroupInput for create.
 */
type ProfessionnelFormGroupInput = IProfessionnel | PartialWithRequiredKeyOf<NewProfessionnel>;

type ProfessionnelFormDefaults = Pick<NewProfessionnel, 'id'>;

type ProfessionnelFormGroupContent = {
  id: FormControl<IProfessionnel['id'] | NewProfessionnel['id']>;
  profession: FormControl<IProfessionnel['profession']>;
  nom: FormControl<IProfessionnel['nom']>;
  prenom: FormControl<IProfessionnel['prenom']>;
  dateNaiss: FormControl<IProfessionnel['dateNaiss']>;
  lieuNaiss: FormControl<IProfessionnel['lieuNaiss']>;
  sexe: FormControl<IProfessionnel['sexe']>;
  telephone: FormControl<IProfessionnel['telephone']>;
  adressePhysique: FormControl<IProfessionnel['adressePhysique']>;
  regionResidence: FormControl<IProfessionnel['regionResidence']>;
  departResidence: FormControl<IProfessionnel['departResidence']>;
  email: FormControl<IProfessionnel['email']>;
  cni: FormControl<IProfessionnel['cni']>;
  user: FormControl<IProfessionnel['user']>;
};

export type ProfessionnelFormGroup = FormGroup<ProfessionnelFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProfessionnelFormService {
  createProfessionnelFormGroup(professionnel: ProfessionnelFormGroupInput = { id: null }): ProfessionnelFormGroup {
    const professionnelRawValue = {
      ...this.getFormDefaults(),
      ...professionnel,
    };
    return new FormGroup<ProfessionnelFormGroupContent>({
      id: new FormControl(
        { value: professionnelRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      profession: new FormControl(professionnelRawValue.profession, {
        validators: [Validators.required],
      }),
      nom: new FormControl(professionnelRawValue.nom, {
        validators: [Validators.required],
      }),
      prenom: new FormControl(professionnelRawValue.prenom, {
        validators: [Validators.required],
      }),
      dateNaiss: new FormControl(professionnelRawValue.dateNaiss),
      lieuNaiss: new FormControl(professionnelRawValue.lieuNaiss),
      sexe: new FormControl(professionnelRawValue.sexe),
      telephone: new FormControl(professionnelRawValue.telephone),
      adressePhysique: new FormControl(professionnelRawValue.adressePhysique),
      regionResidence: new FormControl(professionnelRawValue.regionResidence),
      departResidence: new FormControl(professionnelRawValue.departResidence),
      email: new FormControl(professionnelRawValue.email),
      cni: new FormControl(professionnelRawValue.cni, {
        validators: [Validators.required],
      }),
      user: new FormControl(professionnelRawValue.user),
    });
  }

  getProfessionnel(form: ProfessionnelFormGroup): IProfessionnel | NewProfessionnel {
    return form.getRawValue() as IProfessionnel | NewProfessionnel;
  }

  resetForm(form: ProfessionnelFormGroup, professionnel: ProfessionnelFormGroupInput): void {
    const professionnelRawValue = { ...this.getFormDefaults(), ...professionnel };
    form.reset(
      {
        ...professionnelRawValue,
        id: { value: professionnelRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProfessionnelFormDefaults {
    return {
      id: null,
    };
  }
}
