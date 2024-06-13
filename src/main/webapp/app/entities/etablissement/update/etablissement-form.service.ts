import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEtablissement, NewEtablissement } from '../etablissement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEtablissement for edit and NewEtablissementFormGroupInput for create.
 */
type EtablissementFormGroupInput = IEtablissement | PartialWithRequiredKeyOf<NewEtablissement>;

type EtablissementFormDefaults = Pick<NewEtablissement, 'id' | 'formations'>;

type EtablissementFormGroupContent = {
  id: FormControl<IEtablissement['id'] | NewEtablissement['id']>;
  nomEtablissement: FormControl<IEtablissement['nomEtablissement']>;
  photo: FormControl<IEtablissement['photo']>;
  photoContentType: FormControl<IEtablissement['photoContentType']>;
  region: FormControl<IEtablissement['region']>;
  departement: FormControl<IEtablissement['departement']>;
  email: FormControl<IEtablissement['email']>;
  telephone: FormControl<IEtablissement['telephone']>;
  typeEtablissement: FormControl<IEtablissement['typeEtablissement']>;
  statut: FormControl<IEtablissement['statut']>;
  autreRegion: FormControl<IEtablissement['autreRegion']>;
  autreDepartement: FormControl<IEtablissement['autreDepartement']>;
  cfp: FormControl<IEtablissement['cfp']>;
  lycee: FormControl<IEtablissement['lycee']>;
  filiere: FormControl<IEtablissement['filiere']>;
  serie: FormControl<IEtablissement['serie']>;
  autreFiliere: FormControl<IEtablissement['autreFiliere']>;
  autreSerie: FormControl<IEtablissement['autreSerie']>;
  autreNomEtablissement: FormControl<IEtablissement['autreNomEtablissement']>;
  formations: FormControl<IEtablissement['formations']>;
};

export type EtablissementFormGroup = FormGroup<EtablissementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EtablissementFormService {
  createEtablissementFormGroup(etablissement: EtablissementFormGroupInput = { id: null }): EtablissementFormGroup {
    const etablissementRawValue = {
      ...this.getFormDefaults(),
      ...etablissement,
    };
    return new FormGroup<EtablissementFormGroupContent>({
      id: new FormControl(
        { value: etablissementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nomEtablissement: new FormControl(etablissementRawValue.nomEtablissement),
      photo: new FormControl(etablissementRawValue.photo),
      photoContentType: new FormControl(etablissementRawValue.photoContentType),
      region: new FormControl(etablissementRawValue.region, {
        validators: [Validators.required],
      }),
      departement: new FormControl(etablissementRawValue.departement, {
        validators: [Validators.required],
      }),
      email: new FormControl(etablissementRawValue.email),
      telephone: new FormControl(etablissementRawValue.telephone),
      typeEtablissement: new FormControl(etablissementRawValue.typeEtablissement),
      statut: new FormControl(etablissementRawValue.statut, {
        validators: [Validators.required],
      }),
      autreRegion: new FormControl(etablissementRawValue.autreRegion),
      autreDepartement: new FormControl(etablissementRawValue.autreDepartement),
      cfp: new FormControl(etablissementRawValue.cfp),
      lycee: new FormControl(etablissementRawValue.lycee),
      filiere: new FormControl(etablissementRawValue.filiere),
      serie: new FormControl(etablissementRawValue.serie),
      autreFiliere: new FormControl(etablissementRawValue.autreFiliere),
      autreSerie: new FormControl(etablissementRawValue.autreSerie),
      autreNomEtablissement: new FormControl(etablissementRawValue.autreNomEtablissement),
      formations: new FormControl(etablissementRawValue.formations ?? []),
    });
  }

  getEtablissement(form: EtablissementFormGroup): IEtablissement | NewEtablissement {
    return form.getRawValue() as IEtablissement | NewEtablissement;
  }

  resetForm(form: EtablissementFormGroup, etablissement: EtablissementFormGroupInput): void {
    const etablissementRawValue = { ...this.getFormDefaults(), ...etablissement };
    form.reset(
      {
        ...etablissementRawValue,
        id: { value: etablissementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EtablissementFormDefaults {
    return {
      id: null,
      formations: [],
    };
  }
}
