import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFormation } from '../formation.model';
import { FormationService } from '../service/formation.service';

@Component({
  standalone: true,
  templateUrl: './formation-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FormationDeleteDialogComponent {
  formation?: IFormation;

  protected formationService = inject(FormationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
