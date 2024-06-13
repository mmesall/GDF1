import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IDemandeur } from '../demandeur.model';

@Component({
  standalone: true,
  selector: 'jhi-demandeur-detail',
  templateUrl: './demandeur-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DemandeurDetailComponent {
  demandeur = input<IDemandeur | null>(null);

  previousState(): void {
    window.history.back();
  }
}
