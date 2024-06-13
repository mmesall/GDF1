import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICandidatureE } from '../candidature-e.model';
import { CandidatureEService } from '../service/candidature-e.service';

@Component({
  standalone: true,
  templateUrl: './candidature-e-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CandidatureEDeleteDialogComponent {
  candidatureE?: ICandidatureE;

  protected candidatureEService = inject(CandidatureEService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.candidatureEService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
