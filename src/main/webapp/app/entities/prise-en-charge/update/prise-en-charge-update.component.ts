import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFormation } from 'app/entities/formation/formation.model';
import { FormationService } from 'app/entities/formation/service/formation.service';
import { IBailleur } from 'app/entities/bailleur/bailleur.model';
import { BailleurService } from 'app/entities/bailleur/service/bailleur.service';
import { PriseEnChargeService } from '../service/prise-en-charge.service';
import { IPriseEnCharge } from '../prise-en-charge.model';
import { PriseEnChargeFormService, PriseEnChargeFormGroup } from './prise-en-charge-form.service';

@Component({
  standalone: true,
  selector: 'jhi-prise-en-charge-update',
  templateUrl: './prise-en-charge-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PriseEnChargeUpdateComponent implements OnInit {
  isSaving = false;
  priseEnCharge: IPriseEnCharge | null = null;

  formationsCollection: IFormation[] = [];
  bailleursSharedCollection: IBailleur[] = [];

  protected priseEnChargeService = inject(PriseEnChargeService);
  protected priseEnChargeFormService = inject(PriseEnChargeFormService);
  protected formationService = inject(FormationService);
  protected bailleurService = inject(BailleurService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PriseEnChargeFormGroup = this.priseEnChargeFormService.createPriseEnChargeFormGroup();

  compareFormation = (o1: IFormation | null, o2: IFormation | null): boolean => this.formationService.compareFormation(o1, o2);

  compareBailleur = (o1: IBailleur | null, o2: IBailleur | null): boolean => this.bailleurService.compareBailleur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ priseEnCharge }) => {
      this.priseEnCharge = priseEnCharge;
      if (priseEnCharge) {
        this.updateForm(priseEnCharge);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const priseEnCharge = this.priseEnChargeFormService.getPriseEnCharge(this.editForm);
    if (priseEnCharge.id !== null) {
      this.subscribeToSaveResponse(this.priseEnChargeService.update(priseEnCharge));
    } else {
      this.subscribeToSaveResponse(this.priseEnChargeService.create(priseEnCharge));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPriseEnCharge>>): void {
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

  protected updateForm(priseEnCharge: IPriseEnCharge): void {
    this.priseEnCharge = priseEnCharge;
    this.priseEnChargeFormService.resetForm(this.editForm, priseEnCharge);

    this.formationsCollection = this.formationService.addFormationToCollectionIfMissing<IFormation>(
      this.formationsCollection,
      priseEnCharge.formation,
    );
    this.bailleursSharedCollection = this.bailleurService.addBailleurToCollectionIfMissing<IBailleur>(
      this.bailleursSharedCollection,
      priseEnCharge.bailleur,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.formationService
      .query({ filter: 'priseencharge-is-null' })
      .pipe(map((res: HttpResponse<IFormation[]>) => res.body ?? []))
      .pipe(
        map((formations: IFormation[]) =>
          this.formationService.addFormationToCollectionIfMissing<IFormation>(formations, this.priseEnCharge?.formation),
        ),
      )
      .subscribe((formations: IFormation[]) => (this.formationsCollection = formations));

    this.bailleurService
      .query()
      .pipe(map((res: HttpResponse<IBailleur[]>) => res.body ?? []))
      .pipe(
        map((bailleurs: IBailleur[]) =>
          this.bailleurService.addBailleurToCollectionIfMissing<IBailleur>(bailleurs, this.priseEnCharge?.bailleur),
        ),
      )
      .subscribe((bailleurs: IBailleur[]) => (this.bailleursSharedCollection = bailleurs));
  }
}
