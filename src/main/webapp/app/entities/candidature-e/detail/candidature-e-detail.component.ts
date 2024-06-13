import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICandidatureE } from '../candidature-e.model';

@Component({
  standalone: true,
  selector: 'jhi-candidature-e-detail',
  templateUrl: './candidature-e-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CandidatureEDetailComponent {
  candidatureE = input<ICandidatureE | null>(null);

  previousState(): void {
    window.history.back();
  }
}
