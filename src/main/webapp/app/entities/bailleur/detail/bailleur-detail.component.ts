import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IBailleur } from '../bailleur.model';

@Component({
  standalone: true,
  selector: 'jhi-bailleur-detail',
  templateUrl: './bailleur-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class BailleurDetailComponent {
  bailleur = input<IBailleur | null>(null);

  previousState(): void {
    window.history.back();
  }
}
