import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPriseEnCharge } from '../prise-en-charge.model';

@Component({
  standalone: true,
  selector: 'jhi-prise-en-charge-detail',
  templateUrl: './prise-en-charge-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PriseEnChargeDetailComponent {
  priseEnCharge = input<IPriseEnCharge | null>(null);

  previousState(): void {
    window.history.back();
  }
}
