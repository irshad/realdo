<script>
    import { createEventDispatcher } from "svelte";
    const dispatch = createEventDispatcher();

    let refresher;

    const refreshAction = () => {
        dispatch("refresh");
        setTimeout(() => {
            refresher.complete();
        }, 1000);
    };
</script>

<ion-content>
    <ion-refresher slot="fixed" bind:this={refresher} on:ionRefresh={refreshAction}>
        <ion-refresher-content />
    </ion-refresher>

    <slot />
</ion-content>
