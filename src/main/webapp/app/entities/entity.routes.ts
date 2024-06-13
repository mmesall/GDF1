import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'gdf1App.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'etablissement',
    data: { pageTitle: 'gdf1App.etablissement.home.title' },
    loadChildren: () => import('./etablissement/etablissement.routes'),
  },
  {
    path: 'formation',
    data: { pageTitle: 'gdf1App.formation.home.title' },
    loadChildren: () => import('./formation/formation.routes'),
  },
  {
    path: 'formation-initiale',
    data: { pageTitle: 'gdf1App.formationInitiale.home.title' },
    loadChildren: () => import('./formation-initiale/formation-initiale.routes'),
  },
  {
    path: 'formation-continue',
    data: { pageTitle: 'gdf1App.formationContinue.home.title' },
    loadChildren: () => import('./formation-continue/formation-continue.routes'),
  },
  {
    path: 'concours',
    data: { pageTitle: 'gdf1App.concours.home.title' },
    loadChildren: () => import('./concours/concours.routes'),
  },
  {
    path: 'prise-en-charge',
    data: { pageTitle: 'gdf1App.priseEnCharge.home.title' },
    loadChildren: () => import('./prise-en-charge/prise-en-charge.routes'),
  },
  {
    path: 'bailleur',
    data: { pageTitle: 'gdf1App.bailleur.home.title' },
    loadChildren: () => import('./bailleur/bailleur.routes'),
  },
  {
    path: 'dossier',
    data: { pageTitle: 'gdf1App.dossier.home.title' },
    loadChildren: () => import('./dossier/dossier.routes'),
  },
  {
    path: 'diplome',
    data: { pageTitle: 'gdf1App.diplome.home.title' },
    loadChildren: () => import('./diplome/diplome.routes'),
  },
  {
    path: 'experience',
    data: { pageTitle: 'gdf1App.experience.home.title' },
    loadChildren: () => import('./experience/experience.routes'),
  },
  {
    path: 'candidature-e',
    data: { pageTitle: 'gdf1App.candidatureE.home.title' },
    loadChildren: () => import('./candidature-e/candidature-e.routes'),
  },
  {
    path: 'candidature-p',
    data: { pageTitle: 'gdf1App.candidatureP.home.title' },
    loadChildren: () => import('./candidature-p/candidature-p.routes'),
  },
  {
    path: 'demandeur',
    data: { pageTitle: 'gdf1App.demandeur.home.title' },
    loadChildren: () => import('./demandeur/demandeur.routes'),
  },
  {
    path: 'eleve',
    data: { pageTitle: 'gdf1App.eleve.home.title' },
    loadChildren: () => import('./eleve/eleve.routes'),
  },
  {
    path: 'etudiant',
    data: { pageTitle: 'gdf1App.etudiant.home.title' },
    loadChildren: () => import('./etudiant/etudiant.routes'),
  },
  {
    path: 'professionnel',
    data: { pageTitle: 'gdf1App.professionnel.home.title' },
    loadChildren: () => import('./professionnel/professionnel.routes'),
  },
  {
    path: 'agent',
    data: { pageTitle: 'gdf1App.agent.home.title' },
    loadChildren: () => import('./agent/agent.routes'),
  },
  {
    path: 'service-mfpai',
    data: { pageTitle: 'gdf1App.serviceMFPAI.home.title' },
    loadChildren: () => import('./service-mfpai/service-mfpai.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
