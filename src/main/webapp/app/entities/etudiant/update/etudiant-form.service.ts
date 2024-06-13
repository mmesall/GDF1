import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEtudiant, NewEtudiant } from '../etudiant.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEtudiant for edit and NewEtudiantFormGroupInput for create.
 */
type EtudiantFormGroupInput = IEtudiant | PartialWithRequiredKeyOf<NewEtudiant>;

type EtudiantFormDefaults = Pick<NewEtudiant, 'id'>;

type EtudiantFormGroupContent = {
  id: FormControl<IEtudiant['id'] | NewEtudiant['id']>;
  carteEtudiant: FormControl<IEtudiant['carteEtudiant']>;
  nom: FormControl<IEtudiant['nom']>;
  prenom: FormControl<IEtudiant['prenom']>;
  dateNaiss: FormControl<IEtudiant['dateNaiss']>;
  lieuNaiss: FormControl<IEtudiant['lieuNaiss']>;
  sexe: FormControl<IEtudiant['sexe']>;
  telephone: FormControl<IEtudiant['telephone']>;
  adressePhysique: FormControl<IEtudiant['adressePhysique']>;
  regionResidence: FormControl<IEtudiant['regionResidence']>;
  departResidence: FormControl<IEtudiant['departResidence']>;
  email: FormControl<IEtudiant['email']>;
  cni: FormControl<IEtudiant['cni']>;
  user: FormControl<IEtudiant['user']>;
};

export type EtudiantFormGroup = FormGroup<EtudiantFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EtudiantFormService {
  createEtudiantFormGroup(etudiant: EtudiantFormGroupInput = { id: null }): EtudiantFormGroup {
    const etudiantRawValue = {
      ...this.getFormDefaults(),
      ...etudiant,
    };
    return new FormGroup<EtudiantFormGroupContent>({
      id: new FormControl(
        { value: etudiantRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      carteEtudiant: new FormControl(etudiantRawValue.carteEtudiant, {
        validators: [Validators.required],
      }),
      nom: new FormControl(etudiantRawValue.nom, {
        validators: [Validators.required],
      }),
      prenom: new FormControl(etudiantRawValue.prenom, {
        validators: [Validators.required],
      }),
      dateNaiss: new FormControl(etudiantRawValue.dateNaiss),
      lieuNaiss: new FormControl(etudiantRawValue.lieuNaiss),
      sexe: new FormControl(etudiantRawValue.sexe),
      telephone: new FormControl(etudiantRawValue.telephone),
      adressePhysique: new FormControl(etudiantRawValue.adressePhysique),
      regionResidence: new FormControl(etudiantRawValue.regionResidence),
      departResidence: new FormControl(etudiantRawValue.departResidence),
      email: new FormControl(etudiantRawValue.email),
      cni: new FormControl(etudiantRawValue.cni, {
        validators: [Validators.required],
      }),
      user: new FormControl(etudiantRawValue.user),
    });
  }

  getEtudiant(form: EtudiantFormGroup): IEtudiant | NewEtudiant {
    return form.getRawValue() as IEtudiant | NewEtudiant;
  }

  resetForm(form: EtudiantFormGroup, etudiant: EtudiantFormGroupInput): void {
    const etudiantRawValue = { ...this.getFormDefaults(), ...etudiant };
    form.reset(
      {
        ...etudiantRawValue,
        id: { value: etudiantRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EtudiantFormDefaults {
    return {
      id: null,
    };
  }
}
