import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IConcours } from '../concours.model';
import { ConcoursService } from '../service/concours.service';

@Component({
  standalone: true,
  templateUrl: './concours-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ConcoursDeleteDialogComponent {
  concours?: IConcours;

  protected concoursService = inject(ConcoursService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.concoursService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
