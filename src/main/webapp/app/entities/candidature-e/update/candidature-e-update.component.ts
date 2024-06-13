import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEleve } from 'app/entities/eleve/eleve.model';
import { EleveService } from 'app/entities/eleve/service/eleve.service';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { EtudiantService } from 'app/entities/etudiant/service/etudiant.service';
import { IFormationInitiale } from 'app/entities/formation-initiale/formation-initiale.model';
import { FormationInitialeService } from 'app/entities/formation-initiale/service/formation-initiale.service';
import { IEtablissement } from 'app/entities/etablissement/etablissement.model';
import { EtablissementService } from 'app/entities/etablissement/service/etablissement.service';
import { NomFiliere } from 'app/entities/enumerations/nom-filiere.model';
import { Resultat } from 'app/entities/enumerations/resultat.model';
import { CandidatureEService } from '../service/candidature-e.service';
import { ICandidatureE } from '../candidature-e.model';
import { CandidatureEFormService, CandidatureEFormGroup } from './candidature-e-form.service';

@Component({
  standalone: true,
  selector: 'jhi-candidature-e-update',
  templateUrl: './candidature-e-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CandidatureEUpdateComponent implements OnInit {
  isSaving = false;
  candidatureE: ICandidatureE | null = null;
  nomFiliereValues = Object.keys(NomFiliere);
  resultatValues = Object.keys(Resultat);

  elevesSharedCollection: IEleve[] = [];
  etudiantsSharedCollection: IEtudiant[] = [];
  formationInitialesSharedCollection: IFormationInitiale[] = [];
  etablissementsSharedCollection: IEtablissement[] = [];

  protected candidatureEService = inject(CandidatureEService);
  protected candidatureEFormService = inject(CandidatureEFormService);
  protected eleveService = inject(EleveService);
  protected etudiantService = inject(EtudiantService);
  protected formationInitialeService = inject(FormationInitialeService);
  protected etablissementService = inject(EtablissementService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CandidatureEFormGroup = this.candidatureEFormService.createCandidatureEFormGroup();

  compareEleve = (o1: IEleve | null, o2: IEleve | null): boolean => this.eleveService.compareEleve(o1, o2);

  compareEtudiant = (o1: IEtudiant | null, o2: IEtudiant | null): boolean => this.etudiantService.compareEtudiant(o1, o2);

  compareFormationInitiale = (o1: IFormationInitiale | null, o2: IFormationInitiale | null): boolean =>
    this.formationInitialeService.compareFormationInitiale(o1, o2);

  compareEtablissement = (o1: IEtablissement | null, o2: IEtablissement | null): boolean =>
    this.etablissementService.compareEtablissement(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidatureE }) => {
      this.candidatureE = candidatureE;
      if (candidatureE) {
        this.updateForm(candidatureE);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const candidatureE = this.candidatureEFormService.getCandidatureE(this.editForm);
    if (candidatureE.id !== null) {
      this.subscribeToSaveResponse(this.candidatureEService.update(candidatureE));
    } else {
      this.subscribeToSaveResponse(this.candidatureEService.create(candidatureE));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidatureE>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(candidatureE: ICandidatureE): void {
    this.candidatureE = candidatureE;
    this.candidatureEFormService.resetForm(this.editForm, candidatureE);

    this.elevesSharedCollection = this.eleveService.addEleveToCollectionIfMissing<IEleve>(this.elevesSharedCollection, candidatureE.eleve);
    this.etudiantsSharedCollection = this.etudiantService.addEtudiantToCollectionIfMissing<IEtudiant>(
      this.etudiantsSharedCollection,
      candidatureE.etudiant,
    );
    this.formationInitialesSharedCollection = this.formationInitialeService.addFormationInitialeToCollectionIfMissing<IFormationInitiale>(
      this.formationInitialesSharedCollection,
      candidatureE.formationInitiale,
    );
    this.etablissementsSharedCollection = this.etablissementService.addEtablissementToCollectionIfMissing<IEtablissement>(
      this.etablissementsSharedCollection,
      candidatureE.etablissement,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eleveService
      .query()
      .pipe(map((res: HttpResponse<IEleve[]>) => res.body ?? []))
      .pipe(map((eleves: IEleve[]) => this.eleveService.addEleveToCollectionIfMissing<IEleve>(eleves, this.candidatureE?.eleve)))
      .subscribe((eleves: IEleve[]) => (this.elevesSharedCollection = eleves));

    this.etudiantService
      .query()
      .pipe(map((res: HttpResponse<IEtudiant[]>) => res.body ?? []))
      .pipe(
        map((etudiants: IEtudiant[]) =>
          this.etudiantService.addEtudiantToCollectionIfMissing<IEtudiant>(etudiants, this.candidatureE?.etudiant),
        ),
      )
      .subscribe((etudiants: IEtudiant[]) => (this.etudiantsSharedCollection = etudiants));

    this.formationInitialeService
      .query()
      .pipe(map((res: HttpResponse<IFormationInitiale[]>) => res.body ?? []))
      .pipe(
        map((formationInitiales: IFormationInitiale[]) =>
          this.formationInitialeService.addFormationInitialeToCollectionIfMissing<IFormationInitiale>(
            formationInitiales,
            this.candidatureE?.formationInitiale,
          ),
        ),
      )
      .subscribe((formationInitiales: IFormationInitiale[]) => (this.formationInitialesSharedCollection = formationInitiales));

    this.etablissementService
      .query()
      .pipe(map((res: HttpResponse<IEtablissement[]>) => res.body ?? []))
      .pipe(
        map((etablissements: IEtablissement[]) =>
          this.etablissementService.addEtablissementToCollectionIfMissing<IEtablissement>(etablissements, this.candidatureE?.etablissement),
        ),
      )
      .subscribe((etablissements: IEtablissement[]) => (this.etablissementsSharedCollection = etablissements));
  }
}
