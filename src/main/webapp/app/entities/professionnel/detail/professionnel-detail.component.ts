import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IProfessionnel } from '../professionnel.model';

@Component({
  standalone: true,
  selector: 'jhi-professionnel-detail',
  templateUrl: './professionnel-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ProfessionnelDetailComponent {
  professionnel = input<IProfessionnel | null>(null);

  previousState(): void {
    window.history.back();
  }
}
