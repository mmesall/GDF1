import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IBailleur } from '../bailleur.model';
import { BailleurService } from '../service/bailleur.service';
import { BailleurFormService, BailleurFormGroup } from './bailleur-form.service';

@Component({
  standalone: true,
  selector: 'jhi-bailleur-update',
  templateUrl: './bailleur-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class BailleurUpdateComponent implements OnInit {
  isSaving = false;
  bailleur: IBailleur | null = null;

  protected bailleurService = inject(BailleurService);
  protected bailleurFormService = inject(BailleurFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: BailleurFormGroup = this.bailleurFormService.createBailleurFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bailleur }) => {
      this.bailleur = bailleur;
      if (bailleur) {
        this.updateForm(bailleur);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bailleur = this.bailleurFormService.getBailleur(this.editForm);
    if (bailleur.id !== null) {
      this.subscribeToSaveResponse(this.bailleurService.update(bailleur));
    } else {
      this.subscribeToSaveResponse(this.bailleurService.create(bailleur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBailleur>>): void {
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

  protected updateForm(bailleur: IBailleur): void {
    this.bailleur = bailleur;
    this.bailleurFormService.resetForm(this.editForm, bailleur);
  }
}
