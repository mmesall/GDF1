import { Component, computed, NgZone, inject, OnInit, signal, WritableSignal } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, Subscription, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { sortStateSignal, SortDirective, SortByDirective, type SortState, SortService } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { ParseLinks } from 'app/core/util/parse-links.service';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { EntityArrayResponseType, CandidaturePService } from '../service/candidature-p.service';
import { CandidaturePDeleteDialogComponent } from '../delete/candidature-p-delete-dialog.component';
import { ICandidatureP } from '../candidature-p.model';

@Component({
  standalone: true,
  selector: 'jhi-candidature-p',
  templateUrl: './candidature-p.component.html',
  imports: [
    RouterModule,
    FormsModule,
    SharedModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    InfiniteScrollModule,
  ],
})
export class CandidaturePComponent implements OnInit {
  subscription: Subscription | null = null;
  candidaturePS?: ICandidatureP[];
  isLoading = false;

  sortState = sortStateSignal({});

  itemsPerPage = ITEMS_PER_PAGE;
  links: WritableSignal<{ [key: string]: undefined | { [key: string]: string | undefined } }> = signal({});
  hasMorePage = computed(() => !!this.links().next);
  isFirstFetch = computed(() => Object.keys(this.links()).length === 0);

  public router = inject(Router);
  protected candidaturePService = inject(CandidaturePService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected parseLinks = inject(ParseLinks);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: ICandidatureP): number => this.candidaturePService.getCandidaturePIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.reset()),
        tap(() => this.load()),
      )
      .subscribe();
  }

  reset(): void {
    this.candidaturePS = [];
  }

  loadNextPage(): void {
    this.load();
  }

  delete(candidatureP: ICandidatureP): void {
    const modalRef = this.modalService.open(CandidaturePDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.candidatureP = candidatureP;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  load(): void {
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(event);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.candidaturePS = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: ICandidatureP[] | null): ICandidatureP[] {
    // If there is previous link, data is a infinite scroll pagination content.
    if (this.links().prev) {
      const candidaturePSNew = this.candidaturePS ?? [];
      if (data) {
        for (const d of data) {
          if (candidaturePSNew.map(op => op.id).indexOf(d.id) === -1) {
            candidaturePSNew.push(d);
          }
        }
      }
      return candidaturePSNew;
    }
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    const linkHeader = headers.get('link');
    if (linkHeader) {
      this.links.set(this.parseLinks.parseAll(linkHeader));
    } else {
      this.links.set({});
    }
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      size: this.itemsPerPage,
      eagerload: true,
    };
    if (this.hasMorePage()) {
      Object.assign(queryObject, this.links().next);
    } else if (this.isFirstFetch()) {
      Object.assign(queryObject, { sort: this.sortService.buildSortParam(this.sortState()) });
    }

    return this.candidaturePService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(sortState: SortState): void {
    this.links.set({});

    const queryParamsObj = {
      sort: this.sortService.buildSortParam(sortState),
    };

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }
}