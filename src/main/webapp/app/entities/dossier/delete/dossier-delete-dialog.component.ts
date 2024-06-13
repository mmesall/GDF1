import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDossier } from '../dossier.model';
import { DossierService } from '../service/dossier.service';

@Component({
  standalone: true,
  templateUrl: './dossier-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DossierDeleteDialogComponent {
  dossier?: IDossier;

  protected dossierService = inject(DossierService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dossierService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
