<script>
    export let value = 1;
	export let tab = 1;
</script>

{#if tab == value}
    <div class="tab-panel">
        <slot />
    </div>
{/if}

<style>
    .tab-panel {
        height: calc(100% - 84px);
        padding-bottom: 16px;
    }
</style>