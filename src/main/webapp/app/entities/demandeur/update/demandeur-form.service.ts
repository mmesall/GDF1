import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDemandeur, NewDemandeur } from '../demandeur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDemandeur for edit and NewDemandeurFormGroupInput for create.
 */
type DemandeurFormGroupInput = IDemandeur | PartialWithRequiredKeyOf<NewDemandeur>;

type DemandeurFormDefaults = Pick<NewDemandeur, 'id'>;

type DemandeurFormGroupContent = {
  id: FormControl<IDemandeur['id'] | NewDemandeur['id']>;
  nom: FormControl<IDemandeur['nom']>;
  prenom: FormControl<IDemandeur['prenom']>;
  dateNaiss: FormControl<IDemandeur['dateNaiss']>;
  lieuNaiss: FormControl<IDemandeur['lieuNaiss']>;
  sexe: FormControl<IDemandeur['sexe']>;
  telephone: FormControl<IDemandeur['telephone']>;
  email: FormControl<IDemandeur['email']>;
  profil: FormControl<IDemandeur['profil']>;
  user: FormControl<IDemandeur['user']>;
  dossier: FormControl<IDemandeur['dossier']>;
  eleve: FormControl<IDemandeur['eleve']>;
  etudiant: FormControl<IDemandeur['etudiant']>;
  professionnel: FormControl<IDemandeur['professionnel']>;
};

export type DemandeurFormGroup = FormGroup<DemandeurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DemandeurFormService {
  createDemandeurFormGroup(demandeur: DemandeurFormGroupInput = { id: null }): DemandeurFormGroup {
    const demandeurRawValue = {
      ...this.getFormDefaults(),
      ...demandeur,
    };
    return new FormGroup<DemandeurFormGroupContent>({
      id: new FormControl(
        { value: demandeurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(demandeurRawValue.nom),
      prenom: new FormControl(demandeurRawValue.prenom),
      dateNaiss: new FormControl(demandeurRawValue.dateNaiss),
      lieuNaiss: new FormControl(demandeurRawValue.lieuNaiss),
      sexe: new FormControl(demandeurRawValue.sexe),
      telephone: new FormControl(demandeurRawValue.telephone),
      email: new FormControl(demandeurRawValue.email),
      profil: new FormControl(demandeurRawValue.profil),
      user: new FormControl(demandeurRawValue.user),
      dossier: new FormControl(demandeurRawValue.dossier),
      eleve: new FormControl(demandeurRawValue.eleve),
      etudiant: new FormControl(demandeurRawValue.etudiant),
      professionnel: new FormControl(demandeurRawValue.professionnel),
    });
  }

  getDemandeur(form: DemandeurFormGroup): IDemandeur | NewDemandeur {
    return form.getRawValue() as IDemandeur | NewDemandeur;
  }

  resetForm(form: DemandeurFormGroup, demandeur: DemandeurFormGroupInput): void {
    const demandeurRawValue = { ...this.getFormDefaults(), ...demandeur };
    form.reset(
      {
        ...demandeurRawValue,
        id: { value: demandeurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DemandeurFormDefaults {
    return {
      id: null,
    };
  }
}
