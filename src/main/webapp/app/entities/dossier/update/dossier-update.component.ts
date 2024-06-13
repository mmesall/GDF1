import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { EleveService } from 'app/entities/eleve/service/eleve.service';
import { IEtudiant } from 'app/entities/etudiant/etudiant.model';
import { EtudiantService } from 'app/entities/etudiant/service/etudiant.service';
import { IProfessionnel } from 'app/entities/professionnel/professionnel.model';
import { ProfessionnelService } from 'app/entities/professionnel/service/professionnel.service';
import { NomRegion } from 'app/entities/enumerations/nom-region.model';
import { NomDepartement } from 'app/entities/enumerations/nom-departement.model';
import { TypePiece } from 'app/entities/enumerations/type-piece.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { NiveauEtude } from 'app/entities/enumerations/niveau-etude.model';
import { NomFiliere } from 'app/entities/enumerations/nom-filiere.model';
import { NIVEAUCOMP } from 'app/entities/enumerations/niveaucomp.model';
import { DossierService } from '../service/dossier.service';
import { IDossier } from '../dossier.model';
import { DossierFormService, DossierFormGroup } from './dossier-form.service';

@Component({
  standalone: true,
  selector: 'jhi-dossier-update',
  templateUrl: './dossier-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DossierUpdateComponent implements OnInit {
  isSaving = false;
  dossier: IDossier | null = null;
  nomRegionValues = Object.keys(NomRegion);
  nomDepartementValues = Object.keys(NomDepartement);
  typePieceValues = Object.keys(TypePiece);
  sexeValues = Object.keys(Sexe);
  niveauEtudeValues = Object.keys(NiveauEtude);
  nomFiliereValues = Object.keys(NomFiliere);
  nIVEAUCOMPValues = Object.keys(NIVEAUCOMP);

  elevesCollection: IEleve[] = [];
  etudiantsCollection: IEtudiant[] = [];
  professionnelsCollection: IProfessionnel[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected dossierService = inject(DossierService);
  protected dossierFormService = inject(DossierFormService);
  protected eleveService = inject(EleveService);
  protected etudiantService = inject(EtudiantService);
  protected professionnelService = inject(ProfessionnelService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DossierFormGroup = this.dossierFormService.createDossierFormGroup();

  compareEleve = (o1: IEleve | null, o2: IEleve | null): boolean => this.eleveService.compareEleve(o1, o2);

  compareEtudiant = (o1: IEtudiant | null, o2: IEtudiant | null): boolean => this.etudiantService.compareEtudiant(o1, o2);

  compareProfessionnel = (o1: IProfessionnel | null, o2: IProfessionnel | null): boolean =>
    this.professionnelService.compareProfessionnel(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dossier }) => {
      this.dossier = dossier;
      if (dossier) {
        this.updateForm(dossier);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('gdf1App.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dossier = this.dossierFormService.getDossier(this.editForm);
    if (dossier.id !== null) {
      this.subscribeToSaveResponse(this.dossierService.update(dossier));
    } else {
      this.subscribeToSaveResponse(this.dossierService.create(dossier));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDossier>>): void {
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

  protected updateForm(dossier: IDossier): void {
    this.dossier = dossier;
    this.dossierFormService.resetForm(this.editForm, dossier);

    this.elevesCollection = this.eleveService.addEleveToCollectionIfMissing<IEleve>(this.elevesCollection, dossier.eleve);
    this.etudiantsCollection = this.etudiantService.addEtudiantToCollectionIfMissing<IEtudiant>(this.etudiantsCollection, dossier.etudiant);
    this.professionnelsCollection = this.professionnelService.addProfessionnelToCollectionIfMissing<IProfessionnel>(
      this.professionnelsCollection,
      dossier.professionnel,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eleveService
      .query({ filter: 'dossier-is-null' })
      .pipe(map((res: HttpResponse<IEleve[]>) => res.body ?? []))
      .pipe(map((eleves: IEleve[]) => this.eleveService.addEleveToCollectionIfMissing<IEleve>(eleves, this.dossier?.eleve)))
      .subscribe((eleves: IEleve[]) => (this.elevesCollection = eleves));

    this.etudiantService
      .query({ filter: 'dossier-is-null' })
      .pipe(map((res: HttpResponse<IEtudiant[]>) => res.body ?? []))
      .pipe(
        map((etudiants: IEtudiant[]) =>
          this.etudiantService.addEtudiantToCollectionIfMissing<IEtudiant>(etudiants, this.dossier?.etudiant),
        ),
      )
      .subscribe((etudiants: IEtudiant[]) => (this.etudiantsCollection = etudiants));

    this.professionnelService
      .query({ filter: 'dossier-is-null' })
      .pipe(map((res: HttpResponse<IProfessionnel[]>) => res.body ?? []))
      .pipe(
        map((professionnels: IProfessionnel[]) =>
          this.professionnelService.addProfessionnelToCollectionIfMissing<IProfessionnel>(professionnels, this.dossier?.professionnel),
        ),
      )
      .subscribe((professionnels: IProfessionnel[]) => (this.professionnelsCollection = professionnels));
  }
}
