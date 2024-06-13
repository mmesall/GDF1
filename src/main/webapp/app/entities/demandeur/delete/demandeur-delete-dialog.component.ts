import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDemandeur } from '../demandeur.model';
import { DemandeurService } from '../service/demandeur.service';

@Component({
  standalone: true,
  templateUrl: './demandeur-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DemandeurDeleteDialogComponent {
  demandeur?: IDemandeur;

  protected demandeurService = inject(DemandeurService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandeurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
