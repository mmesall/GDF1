import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IBailleur } from '../bailleur.model';
import { BailleurService } from '../service/bailleur.service';

@Component({
  standalone: true,
  templateUrl: './bailleur-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class BailleurDeleteDialogComponent {
  bailleur?: IBailleur;

  protected bailleurService = inject(BailleurService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bailleurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
