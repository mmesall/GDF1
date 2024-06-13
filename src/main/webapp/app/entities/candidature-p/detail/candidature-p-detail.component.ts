import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICandidatureP } from '../candidature-p.model';

@Component({
  standalone: true,
  selector: 'jhi-candidature-p-detail',
  templateUrl: './candidature-p-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CandidaturePDetailComponent {
  candidatureP = input<ICandidatureP | null>(null);

  previousState(): void {
    window.history.back();
  }
}
