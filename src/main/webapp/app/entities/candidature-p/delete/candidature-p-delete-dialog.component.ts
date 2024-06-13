import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICandidatureP } from '../candidature-p.model';
import { CandidaturePService } from '../service/candidature-p.service';

@Component({
  standalone: true,
  templateUrl: './candidature-p-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CandidaturePDeleteDialogComponent {
  candidatureP?: ICandidatureP;

  protected candidaturePService = inject(CandidaturePService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.candidaturePService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
