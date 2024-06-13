import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEleve, NewEleve } from '../eleve.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEleve for edit and NewEleveFormGroupInput for create.
 */
type EleveFormGroupInput = IEleve | PartialWithRequiredKeyOf<NewEleve>;

type EleveFormDefaults = Pick<NewEleve, 'id'>;

type EleveFormGroupContent = {
  id: FormControl<IEleve['id'] | NewEleve['id']>;
  nom: FormControl<IEleve['nom']>;
  prenom: FormControl<IEleve['prenom']>;
  dateNaiss: FormControl<IEleve['dateNaiss']>;
  lieuNaiss: FormControl<IEleve['lieuNaiss']>;
  sexe: FormControl<IEleve['sexe']>;
  telephone: FormControl<IEleve['telephone']>;
  adressePhysique: FormControl<IEleve['adressePhysique']>;
  regionResidence: FormControl<IEleve['regionResidence']>;
  departResidence: FormControl<IEleve['departResidence']>;
  niveauEtude: FormControl<IEleve['niveauEtude']>;
  cni: FormControl<IEleve['cni']>;
  user: FormControl<IEleve['user']>;
};

export type EleveFormGroup = FormGroup<EleveFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EleveFormService {
  createEleveFormGroup(eleve: EleveFormGroupInput = { id: null }): EleveFormGroup {
    const eleveRawValue = {
      ...this.getFormDefaults(),
      ...eleve,
    };
    return new FormGroup<EleveFormGroupContent>({
      id: new FormControl(
        { value: eleveRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(eleveRawValue.nom, {
        validators: [Validators.required],
      }),
      prenom: new FormControl(eleveRawValue.prenom, {
        validators: [Validators.required],
      }),
      dateNaiss: new FormControl(eleveRawValue.dateNaiss),
      lieuNaiss: new FormControl(eleveRawValue.lieuNaiss),
      sexe: new FormControl(eleveRawValue.sexe),
      telephone: new FormControl(eleveRawValue.telephone),
      adressePhysique: new FormControl(eleveRawValue.adressePhysique),
      regionResidence: new FormControl(eleveRawValue.regionResidence),
      departResidence: new FormControl(eleveRawValue.departResidence),
      niveauEtude: new FormControl(eleveRawValue.niveauEtude, {
        validators: [Validators.required],
      }),
      cni: new FormControl(eleveRawValue.cni),
      user: new FormControl(eleveRawValue.user),
    });
  }

  getEleve(form: EleveFormGroup): IEleve | NewEleve {
    return form.getRawValue() as IEleve | NewEleve;
  }

  resetForm(form: EleveFormGroup, eleve: EleveFormGroupInput): void {
    const eleveRawValue = { ...this.getFormDefaults(), ...eleve };
    form.reset(
      {
        ...eleveRawValue,
        id: { value: eleveRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EleveFormDefaults {
    return {
      id: null,
    };
  }
}
