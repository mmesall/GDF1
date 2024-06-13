import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFormationContinue } from '../formation-continue.model';
import { FormationContinueService } from '../service/formation-continue.service';

@Component({
  standalone: true,
  templateUrl: './formation-continue-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FormationContinueDeleteDialogComponent {
  formationContinue?: IFormationContinue;

  protected formationContinueService = inject(FormationContinueService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formationContinueService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
