import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDossier, NewDossier } from '../dossier.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDossier for edit and NewDossierFormGroupInput for create.
 */
type DossierFormGroupInput = IDossier | PartialWithRequiredKeyOf<NewDossier>;

type DossierFormDefaults = Pick<NewDossier, 'id'>;

type DossierFormGroupContent = {
  id: FormControl<IDossier['id'] | NewDossier['id']>;
  numDossier: FormControl<IDossier['numDossier']>;
  prenom: FormControl<IDossier['prenom']>;
  nom: FormControl<IDossier['nom']>;
  nomUtilisateur: FormControl<IDossier['nomUtilisateur']>;
  dateNaiss: FormControl<IDossier['dateNaiss']>;
  lieuNaiss: FormControl<IDossier['lieuNaiss']>;
  regionNaiss: FormControl<IDossier['regionNaiss']>;
  departementNaiss: FormControl<IDossier['departementNaiss']>;
  typePiece: FormControl<IDossier['typePiece']>;
  numeroPiece: FormControl<IDossier['numeroPiece']>;
  sexe: FormControl<IDossier['sexe']>;
  regionResidence: FormControl<IDossier['regionResidence']>;
  depResidence: FormControl<IDossier['depResidence']>;
  adresseResidence: FormControl<IDossier['adresseResidence']>;
  telephone1: FormControl<IDossier['telephone1']>;
  telephone2: FormControl<IDossier['telephone2']>;
  email: FormControl<IDossier['email']>;
  niveauFormation: FormControl<IDossier['niveauFormation']>;
  specialite: FormControl<IDossier['specialite']>;
  intituleDiplome: FormControl<IDossier['intituleDiplome']>;
  diplome: FormControl<IDossier['diplome']>;
  diplomeContentType: FormControl<IDossier['diplomeContentType']>;
  anneeObtention: FormControl<IDossier['anneeObtention']>;
  lieuObtention: FormControl<IDossier['lieuObtention']>;
  cv: FormControl<IDossier['cv']>;
  cvContentType: FormControl<IDossier['cvContentType']>;
  lettreMotivation: FormControl<IDossier['lettreMotivation']>;
  lettreMotivationContentType: FormControl<IDossier['lettreMotivationContentType']>;
  profession: FormControl<IDossier['profession']>;
  autreSpecialite: FormControl<IDossier['autreSpecialite']>;
  nomCompetence: FormControl<IDossier['nomCompetence']>;
  niveauCompetence: FormControl<IDossier['niveauCompetence']>;
  intituleExperience: FormControl<IDossier['intituleExperience']>;
  posteOccupe: FormControl<IDossier['posteOccupe']>;
  dateDebut: FormControl<IDossier['dateDebut']>;
  dateFin: FormControl<IDossier['dateFin']>;
  nomEntreprise: FormControl<IDossier['nomEntreprise']>;
  mission: FormControl<IDossier['mission']>;
  eleve: FormControl<IDossier['eleve']>;
  etudiant: FormControl<IDossier['etudiant']>;
  professionnel: FormControl<IDossier['professionnel']>;
};

export type DossierFormGroup = FormGroup<DossierFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DossierFormService {
  createDossierFormGroup(dossier: DossierFormGroupInput = { id: null }): DossierFormGroup {
    const dossierRawValue = {
      ...this.getFormDefaults(),
      ...dossier,
    };
    return new FormGroup<DossierFormGroupContent>({
      id: new FormControl(
        { value: dossierRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      numDossier: new FormControl(dossierRawValue.numDossier),
      prenom: new FormControl(dossierRawValue.prenom, {
        validators: [Validators.required],
      }),
      nom: new FormControl(dossierRawValue.nom, {
        validators: [Validators.required],
      }),
      nomUtilisateur: new FormControl(dossierRawValue.nomUtilisateur),
      dateNaiss: new FormControl(dossierRawValue.dateNaiss),
      lieuNaiss: new FormControl(dossierRawValue.lieuNaiss),
      regionNaiss: new FormControl(dossierRawValue.regionNaiss),
      departementNaiss: new FormControl(dossierRawValue.departementNaiss),
      typePiece: new FormControl(dossierRawValue.typePiece),
      numeroPiece: new FormControl(dossierRawValue.numeroPiece),
      sexe: new FormControl(dossierRawValue.sexe),
      regionResidence: new FormControl(dossierRawValue.regionResidence),
      depResidence: new FormControl(dossierRawValue.depResidence),
      adresseResidence: new FormControl(dossierRawValue.adresseResidence),
      telephone1: new FormControl(dossierRawValue.telephone1),
      telephone2: new FormControl(dossierRawValue.telephone2),
      email: new FormControl(dossierRawValue.email),
      niveauFormation: new FormControl(dossierRawValue.niveauFormation),
      specialite: new FormControl(dossierRawValue.specialite),
      intituleDiplome: new FormControl(dossierRawValue.intituleDiplome),
      diplome: new FormControl(dossierRawValue.diplome),
      diplomeContentType: new FormControl(dossierRawValue.diplomeContentType),
      anneeObtention: new FormControl(dossierRawValue.anneeObtention),
      lieuObtention: new FormControl(dossierRawValue.lieuObtention),
      cv: new FormControl(dossierRawValue.cv),
      cvContentType: new FormControl(dossierRawValue.cvContentType),
      lettreMotivation: new FormControl(dossierRawValue.lettreMotivation),
      lettreMotivationContentType: new FormControl(dossierRawValue.lettreMotivationContentType),
      profession: new FormControl(dossierRawValue.profession),
      autreSpecialite: new FormControl(dossierRawValue.autreSpecialite),
      nomCompetence: new FormControl(dossierRawValue.nomCompetence),
      niveauCompetence: new FormControl(dossierRawValue.niveauCompetence),
      intituleExperience: new FormControl(dossierRawValue.intituleExperience),
      posteOccupe: new FormControl(dossierRawValue.posteOccupe, {
        validators: [Validators.required],
      }),
      dateDebut: new FormControl(dossierRawValue.dateDebut, {
        validators: [Validators.required],
      }),
      dateFin: new FormControl(dossierRawValue.dateFin, {
        validators: [Validators.required],
      }),
      nomEntreprise: new FormControl(dossierRawValue.nomEntreprise, {
        validators: [Validators.required],
      }),
      mission: new FormControl(dossierRawValue.mission),
      eleve: new FormControl(dossierRawValue.eleve),
      etudiant: new FormControl(dossierRawValue.etudiant),
      professionnel: new FormControl(dossierRawValue.professionnel),
    });
  }

  getDossier(form: DossierFormGroup): IDossier | NewDossier {
    return form.getRawValue() as IDossier | NewDossier;
  }

  resetForm(form: DossierFormGroup, dossier: DossierFormGroupInput): void {
    const dossierRawValue = { ...this.getFormDefaults(), ...dossier };
    form.reset(
      {
        ...dossierRawValue,
        id: { value: dossierRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DossierFormDefaults {
    return {
      id: null,
    };
  }
}
