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
import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';
import { NomEtablissement } from 'app/entities/enumerations/nom-etablissement.model';
import { NomRegion } from 'app/entities/enumerations/nom-region.model';
import { NomDepartement } from 'app/entities/enumerations/nom-departement.model';
import { TypeEtablissement } from 'app/entities/enumerations/type-etablissement.model';
import { StatutEtab } from 'app/entities/enumerations/statut-etab.model';
import { CFP } from 'app/entities/enumerations/cfp.model';
import { LYCEE } from 'app/entities/enumerations/lycee.model';
import { NomFiliere } from 'app/entities/enumerations/nom-filiere.model';
import { NomSerie } from 'app/entities/enumerations/nom-serie.model';
import { EtablissementService } from '../service/etablissement.service';
import { IEtablissement } from '../etablissement.model';
import { EtablissementFormService, EtablissementFormGroup } from './etablissement-form.service';

@Component({
  standalone: true,
  selector: 'jhi-etablissement-update',
  templateUrl: './etablissement-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EtablissementUpdateComponent implements OnInit {
  isSaving = false;
  etablissement: IEtablissement | null = null;
  nomEtablissementValues = Object.keys(NomEtablissement);
  nomRegionValues = Object.keys(NomRegion);
  nomDepartementValues = Object.keys(NomDepartement);
  typeEtablissementValues = Object.keys(TypeEtablissement);
  statutEtabValues = Object.keys(StatutEtab);
  cFPValues = Object.keys(CFP);
  lYCEEValues = Object.keys(LYCEE);
  nomFiliereValues = Object.keys(NomFiliere);
  nomSerieValues = Object.keys(NomSerie);

  formationsSharedCollection: IFormation[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected etablissementService = inject(EtablissementService);
  protected etablissementFormService = inject(EtablissementFormService);
  protected formationService = inject(FormationService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EtablissementFormGroup = this.etablissementFormService.createEtablissementFormGroup();

  compareFormation = (o1: IFormation | null, o2: IFormation | null): boolean => this.formationService.compareFormation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etablissement }) => {
      this.etablissement = etablissement;
      if (etablissement) {
        this.updateForm(etablissement);
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
    const etablissement = this.etablissementFormService.getEtablissement(this.editForm);
    if (etablissement.id !== null) {
      this.subscribeToSaveResponse(this.etablissementService.update(etablissement));
    } else {
      this.subscribeToSaveResponse(this.etablissementService.create(etablissement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtablissement>>): void {
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

  protected updateForm(etablissement: IEtablissement): void {
    this.etablissement = etablissement;
    this.etablissementFormService.resetForm(this.editForm, etablissement);

    this.formationsSharedCollection = this.formationService.addFormationToCollectionIfMissing<IFormation>(
      this.formationsSharedCollection,
      ...(etablissement.formations ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.formationService
      .query()
      .pipe(map((res: HttpResponse<IFormation[]>) => res.body ?? []))
      .pipe(
        map((formations: IFormation[]) =>
          this.formationService.addFormationToCollectionIfMissing<IFormation>(formations, ...(this.etablissement?.formations ?? [])),
        ),
      )
      .subscribe((formations: IFormation[]) => (this.formationsSharedCollection = formations));
  }
}
