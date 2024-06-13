import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPriseEnCharge } from '../prise-en-charge.model';
import { PriseEnChargeService } from '../service/prise-en-charge.service';

@Component({
  standalone: true,
  templateUrl: './prise-en-charge-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PriseEnChargeDeleteDialogComponent {
  priseEnCharge?: IPriseEnCharge;

  protected priseEnChargeService = inject(PriseEnChargeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.priseEnChargeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
