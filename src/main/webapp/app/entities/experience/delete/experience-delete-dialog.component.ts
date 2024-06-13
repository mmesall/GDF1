import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IExperience } from '../experience.model';
import { ExperienceService } from '../service/experience.service';

@Component({
  standalone: true,
  templateUrl: './experience-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ExperienceDeleteDialogComponent {
  experience?: IExperience;

  protected experienceService = inject(ExperienceService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.experienceService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
