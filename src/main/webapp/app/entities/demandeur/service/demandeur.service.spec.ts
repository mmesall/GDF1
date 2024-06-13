import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDemandeur } from '../demandeur.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../demandeur.test-samples';

import { DemandeurService, RestDemandeur } from './demandeur.service';

const requireRestSample: RestDemandeur = {
  ...sampleWithRequiredData,
  dateNaiss: sampleWithRequiredData.dateNaiss?.format(DATE_FORMAT),
};

describe('Demandeur Service', () => {
  let service: DemandeurService;
  let httpMock: HttpTestingController;
  let expectedResult: IDemandeur | IDemandeur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DemandeurService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Demandeur', () => {
      const demandeur = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(demandeur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Demandeur', () => {
      const demandeur = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(demandeur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Demandeur', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Demandeur', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Demandeur', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDemandeurToCollectionIfMissing', () => {
      it('should add a Demandeur to an empty array', () => {
        const demandeur: IDemandeur = sampleWithRequiredData;
        expectedResult = service.addDemandeurToCollectionIfMissing([], demandeur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeur);
      });

      it('should not add a Demandeur to an array that contains it', () => {
        const demandeur: IDemandeur = sampleWithRequiredData;
        const demandeurCollection: IDemandeur[] = [
          {
            ...demandeur,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDemandeurToCollectionIfMissing(demandeurCollection, demandeur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Demandeur to an array that doesn't contain it", () => {
        const demandeur: IDemandeur = sampleWithRequiredData;
        const demandeurCollection: IDemandeur[] = [sampleWithPartialData];
        expectedResult = service.addDemandeurToCollectionIfMissing(demandeurCollection, demandeur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeur);
      });

      it('should add only unique Demandeur to an array', () => {
        const demandeurArray: IDemandeur[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const demandeurCollection: IDemandeur[] = [sampleWithRequiredData];
        expectedResult = service.addDemandeurToCollectionIfMissing(demandeurCollection, ...demandeurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const demandeur: IDemandeur = sampleWithRequiredData;
        const demandeur2: IDemandeur = sampleWithPartialData;
        expectedResult = service.addDemandeurToCollectionIfMissing([], demandeur, demandeur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeur);
        expect(expectedResult).toContain(demandeur2);
      });

      it('should accept null and undefined values', () => {
        const demandeur: IDemandeur = sampleWithRequiredData;
        expectedResult = service.addDemandeurToCollectionIfMissing([], null, demandeur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeur);
      });

      it('should return initial array if no Demandeur is added', () => {
        const demandeurCollection: IDemandeur[] = [sampleWithRequiredData];
        expectedResult = service.addDemandeurToCollectionIfMissing(demandeurCollection, undefined, null);
        expect(expectedResult).toEqual(demandeurCollection);
      });
    });

    describe('compareDemandeur', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDemandeur(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDemandeur(entity1, entity2);
        const compareResult2 = service.compareDemandeur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDemandeur(entity1, entity2);
        const compareResult2 = service.compareDemandeur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDemandeur(entity1, entity2);
        const compareResult2 = service.compareDemandeur(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
